package de.leyn.noteapp.db

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Created by Leyn on 14.11.2021.
 */
@Database(entities = [NoteBean::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}