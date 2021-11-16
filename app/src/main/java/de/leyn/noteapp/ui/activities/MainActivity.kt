package de.leyn.noteapp.ui.activities

import android.content.Intent
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.forEach
import androidx.recyclerview.widget.LinearLayoutManager
import de.leyn.noteapp.App
import de.leyn.noteapp.R
import de.leyn.noteapp.adapter.NoteRecyclerAdapter
import de.leyn.noteapp.databinding.ActivityMainBinding
import de.leyn.noteapp.db.NoteBean
import de.leyn.noteapp.ui.AddNoteDialog
import de.leyn.noteapp.ui.viewmodel.MainViewModel
import de.leyn.noteapp.ui.viewmodel.MainViewModelFactory

class MainActivity : AppCompatActivity(), AddNoteDialog.AddNoteDialogListener,
    NoteRecyclerAdapter.NoteViewHolder.OnNoteClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var recyclerAdapter: NoteRecyclerAdapter
    private val noteList: MutableList<NoteBean> = mutableListOf()

    private var recyclerViewInitialized = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val db = (application as App).getDB()
        mainViewModel = MainViewModelFactory(db).create(MainViewModel::class.java)

        fetchNotesList()

        binding.fab.setOnClickListener { _ ->
            val dialog = AddNoteDialog()
            dialog.show(supportFragmentManager, "test")
        }

        mainViewModel.notes.observe(this, {
            noteList.clear()
            noteList.addAll(it)

            if (!recyclerViewInitialized) {
                initRecyclerView()
            } else {
                recyclerAdapter.notifyDataSetChanged()
            }
        })
    }

    private fun initRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerAdapter = NoteRecyclerAdapter(noteList, this)
        binding.recyclerView.adapter = recyclerAdapter
        recyclerViewInitialized = true
    }

    private fun fetchNotesList() {
        mainViewModel.fetchNotesFromDB()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        menu.forEach { item ->
            item.icon.colorFilter = PorterDuffColorFilter(
                resources.getColor(R.color.onPrimary, null),
                PorterDuff.Mode.SRC_ATOP
            )
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.action_refresh -> {
                fetchNotesList()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDialogPositiveClick(newNote: NoteBean) {
        mainViewModel.saveNoteToDB(newNote)
        fetchNotesList()
    }

    override fun onNoteClicked(position: Int) {
        val intent = Intent(this, SingleNoteActivity::class.java)
        intent.putExtra("note", noteList[position])
        startActivity(intent)
    }


}