package de.leyn.noteapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import de.leyn.noteapp.db.NoteBean
import de.leyn.noteapp.db.NoteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by Leyn on 16.11.2021.
 */
class SingleNoteViewModel(
    val db: NoteDatabase
) : ViewModel() {

    lateinit var currentNote: NoteBean
    var isNewNote: Boolean = false

    fun insertNote(noteBean: NoteBean) {
        CoroutineScope(Dispatchers.IO).launch {
            db.noteDao().insertNote(noteBean)
        }
    }

    fun updateNote() {
        CoroutineScope(Dispatchers.IO).launch {
            db.noteDao().updateNote(currentNote)
        }
    }
}