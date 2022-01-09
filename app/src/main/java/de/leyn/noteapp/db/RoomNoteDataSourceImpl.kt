package de.leyn.noteapp.db

import android.content.Context

/**
 * Created by Leyn on 09.01.2022.
 */
class RoomNoteDataSourceImpl(context: Context) : NoteDataSource {
    private val noteDao = DatabaseService.getInstance(context).noteDao()

    override fun getAllNotes(): List<NoteBean> {
        return noteDao.getAllNotes()
    }

    override fun insertNote(note: NoteBean) {
        return noteDao.insertNote(note)
    }

    override fun deleteNote(note: NoteBean) {
        return noteDao.deleteNote(note)
    }

    override fun updateNote(note: NoteBean) {
        return noteDao.updateNote(note)
    }

}