package de.leyn.noteapp.data.repositories

import android.content.Context
import de.leyn.noteapp.data.data_source.NoteDatabase
import de.leyn.noteapp.domain.model.Note

/**
 * Created by Leyn on 09.01.2022.
 */
class RoomNoteRepositoryImpl(context: Context) : NoteRepository {
    private val noteDao = NoteDatabase.getInstance(context).noteDao()

    override fun getAllNotes(): List<Note> {
        return noteDao.getAllNotes()
    }

    override fun insertNote(note: Note) {
        return noteDao.insertNote(note)
    }

    override fun deleteNote(note: Note) {
        return noteDao.deleteNote(note)
    }

    override fun updateNote(note: Note) {
        return noteDao.updateNote(note)
    }

}