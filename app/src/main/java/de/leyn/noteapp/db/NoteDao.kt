package de.leyn.noteapp.db

import androidx.room.*

/**
 * Created by Leyn on 14.11.2021.
 */
@Dao
interface NoteDao {

    @Query("SELECT * FROM notes")
    fun getAllNotes(): List<NoteBean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNote(note: NoteBean)

    @Delete
    fun deleteNote(note: NoteBean)

    @Update
    fun updateNote(note: NoteBean)

}