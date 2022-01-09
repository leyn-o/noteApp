package de.leyn.noteapp.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import de.leyn.noteapp.App
import de.leyn.noteapp.databinding.ActivityOverviewListBinding
import de.leyn.noteapp.db.NoteBean
import de.leyn.noteapp.db.RoomNoteDataSourceImpl
import de.leyn.noteapp.ui.DeleteConfirmationDialog
import de.leyn.noteapp.ui.adapter.NoteRecyclerAdapter
import de.leyn.noteapp.ui.viewmodel.NoteViewModel
import de.leyn.noteapp.ui.viewmodel.ViewModelFactory

class OverviewListActivity : AppCompatActivity(),
    NoteRecyclerAdapter.NoteViewHolder.OnNoteClickListener,
    DeleteConfirmationDialog.DeleteNoteDialogListener {

    private lateinit var binding: ActivityOverviewListBinding
    private lateinit var viewModel: NoteViewModel
    private lateinit var recyclerAdapter: NoteRecyclerAdapter
    private val noteList: MutableList<NoteBean> = mutableListOf()

    private var recyclerViewInitialized = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOverviewListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val dataSource = RoomNoteDataSourceImpl(applicationContext)
        viewModel = ViewModelFactory(dataSource).create(NoteViewModel::class.java)

        fetchNotesList()

        binding.fab.setOnClickListener { _ ->
            val intent = Intent(this, SingleNoteActivity::class.java)
            startActivity(intent)
        }

        viewModel.notes.observe(this, {
            noteList.clear()
            noteList.addAll(it)

            if (!recyclerViewInitialized) {
                initRecyclerView()
            } else {
                recyclerAdapter.notifyDataSetChanged()
            }
        })
    }

    override fun onRestart() {
        super.onRestart()
        fetchNotesList()
    }


    private fun initRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerAdapter = NoteRecyclerAdapter(noteList, this)
        binding.recyclerView.adapter = recyclerAdapter
        recyclerViewInitialized = true
    }

    private fun fetchNotesList() {
        viewModel.fetchNotesFromDB()
    }

    override fun onNoteClicked(position: Int) {
        val intent = Intent(this, SingleNoteActivity::class.java)
        intent.putExtra(App.INTENT_NOTE, noteList[position])
        startActivity(intent)
    }

    override fun onNoteDeleteClicked(position: Int) {
        val dialog = DeleteConfirmationDialog(position, noteList[position].title)
        dialog.show(supportFragmentManager, "deleteDialod")
    }

    override fun onDialogPositiveClick(position: Int) {
        viewModel.deleteNoteFromDB(noteList[position])
        noteList.removeAt(position)
        recyclerAdapter.notifyItemRemoved(position)
    }

}