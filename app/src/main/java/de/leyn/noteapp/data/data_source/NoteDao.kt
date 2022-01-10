package de.leyn.noteapp.data.data_source

import androidx.room.*
import de.leyn.noteapp.domain.model.Note

/**
 * Created by Leyn on 14.11.2021.
 */
@Dao
interface NoteDao {

    @Query("SELECT * FROM notes")
    fun getAllNotes(): List<Note>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNote(note: Note)

    @Delete
    fun deleteNote(note: Note)

    @Update
    fun updateNote(note: Note)

}