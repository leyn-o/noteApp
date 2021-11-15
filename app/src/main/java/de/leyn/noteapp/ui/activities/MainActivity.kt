package de.leyn.noteapp.ui.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import de.leyn.noteapp.App
import de.leyn.noteapp.ui.AddNoteDialog
import de.leyn.noteapp.R
import de.leyn.noteapp.databinding.ActivityMainBinding
import de.leyn.noteapp.db.NoteBean
import de.leyn.noteapp.ui.viewmodel.MainViewModel
import de.leyn.noteapp.ui.viewmodel.MainViewModelFactory

class MainActivity : AppCompatActivity(), AddNoteDialog.AddNoteDialogListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)


        val db = (application as App).getDB()
        mainViewModel = MainViewModelFactory(db).create(MainViewModel::class.java)



        binding.fab.setOnClickListener { view ->
            val dialog = AddNoteDialog()
            dialog.show(supportFragmentManager, "test")
        }

        initList()

        // listener for Live data that adds the content to the recycler view
        // recycler view needs to be updated

    }

    private fun initList() {
        // init RecyclerView
        mainViewModel.fetchNotesFromDB()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.action_refresh -> {
                initList()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDialogPositiveClick(newNote: NoteBean) {
        mainViewModel.saveNoteToDB(newNote)
    }


}