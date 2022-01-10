package de.leyn.noteapp.data.repositories

import de.leyn.noteapp.domain.model.Note

/**
 * Created by Leyn on 09.01.2022.
 */
interface NoteRepository {
    fun getAllNotes(): List<Note>
    fun insertNote(note: Note)
    fun deleteNote(note: Note)
    fun updateNote(note: Note)
}