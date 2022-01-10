package de.leyn.noteapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import de.leyn.noteapp.domain.model.Note
import de.leyn.noteapp.data.repositories.NoteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by Leyn on 14.11.2021.
 */
class NoteViewModel(
    private val repository: NoteRepository
) : ViewModel() {

    private val _notes = MutableLiveData<List<Note>>().apply {
        value = listOf()
    }
    val notes: LiveData<List<Note>> = _notes
    lateinit var singleNote: Note
    var isNewNote: Boolean = false

    fun fetchNotesFromDB() {
        CoroutineScope(Dispatchers.IO).launch {
            val allNotes = repository.getAllNotes()
            CoroutineScope(Dispatchers.Main).launch {
                _notes.value = allNotes
            }
        }
    }

    fun deleteNoteFromDB(note: Note) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.deleteNote(note)
        }
    }

    fun insertNote(note: Note) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.insertNote(note)
        }
    }

    fun updateNote() {
        CoroutineScope(Dispatchers.IO).launch {
            repository.updateNote(singleNote)
        }
    }
}