package de.leyn.noteapp.db

/**
 * Created by Leyn on 09.01.2022.
 */
interface NoteDataSource {
    fun getAllNotes(): List<NoteBean>
    fun insertNote(note: NoteBean)
    fun deleteNote(note: NoteBean)
    fun updateNote(note: NoteBean)
}