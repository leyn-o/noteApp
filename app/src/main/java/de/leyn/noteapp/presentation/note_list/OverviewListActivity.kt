package de.leyn.noteapp.presentation.note_list

import android.content.Intent
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import de.leyn.noteapp.App
import de.leyn.noteapp.R
import de.leyn.noteapp.databinding.ActivityOverviewListBinding
import de.leyn.noteapp.domain.model.Note
import de.leyn.noteapp.data.repositories.RoomNoteRepositoryImpl
import de.leyn.noteapp.extensions.convertToDate
import de.leyn.noteapp.presentation.add_edit_note.SingleNoteActivity
import de.leyn.noteapp.presentation.note_list.adapter.NoteRecyclerAdapter
import de.leyn.noteapp.presentation.note_list.dialog.DeleteConfirmationDialog
import de.leyn.noteapp.presentation.viewmodel.NoteViewModel
import de.leyn.noteapp.presentation.viewmodel.ViewModelFactory

class OverviewListActivity : AppCompatActivity(),
    NoteRecyclerAdapter.NoteViewHolder.OnNoteClickListener,
    DeleteConfirmationDialog.DeleteNoteDialogListener {

    private lateinit var binding: ActivityOverviewListBinding
    private lateinit var viewModel: NoteViewModel
    private lateinit var recyclerAdapter: NoteRecyclerAdapter
    private val noteList: MutableList<Note> = mutableListOf()

    private var recyclerViewInitialized = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOverviewListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        changeMenuIcon()

        val dataSource = RoomNoteRepositoryImpl(applicationContext)
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

    private fun changeMenuIcon() {
        val menuIcon = ResourcesCompat.getDrawable(resources, R.drawable.sort, null)
        menuIcon?.colorFilter = PorterDuffColorFilter(
            resources.getColor(R.color.onPrimary, null),
            PorterDuff.Mode.SRC_ATOP
        )
        binding.toolbar.overflowIcon = menuIcon
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.sort_order_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.dateDescending -> {
                noteList.sortByDescending { it.createdDate.convertToDate() }
                recyclerAdapter.notifyDataSetChanged()
                true
            }
            R.id.dateAscending -> {
                noteList.sortBy { it.createdDate.convertToDate() }
                recyclerAdapter.notifyDataSetChanged()
                true
            }
            R.id.colorDescending -> {
                noteList.sortByDescending { it.color }
                recyclerAdapter.notifyDataSetChanged()
                true
            }
            R.id.colorAscending -> {
                noteList.sortBy { it.color }
                recyclerAdapter.notifyDataSetChanged()
                true
            }
            R.id.titleDescending -> {
                noteList.sortByDescending { it.title }
                recyclerAdapter.notifyDataSetChanged()
                true
            }
            R.id.titleAscending -> {
                noteList.sortBy { it.title }
                recyclerAdapter.notifyDataSetChanged()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }
}