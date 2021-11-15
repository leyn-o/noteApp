package de.leyn.noteapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import de.leyn.noteapp.db.NoteBean
import de.leyn.noteapp.db.NoteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by Leyn on 14.11.2021.
 */
class MainViewModel(
    val db: NoteDatabase
) : ViewModel() {

    private val _notes = MutableLiveData<List<NoteBean>>().apply {
        value = listOf()
    }
    val notes: LiveData<List<NoteBean>> = _notes

    fun fetchNotesFromDB() {
        CoroutineScope(Dispatchers.IO).launch {
            val allNotes = db.noteDao().getAllNotes()
            CoroutineScope(Dispatchers.Main).launch {
                _notes.value = allNotes
            }
        }
    }

    fun saveNoteToDB(note: NoteBean) {
        CoroutineScope(Dispatchers.IO).launch {
            db.noteDao().insertNote(note)
        }
    }
}