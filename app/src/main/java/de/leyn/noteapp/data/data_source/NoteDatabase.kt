package de.leyn.noteapp.data.data_source

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import de.leyn.noteapp.domain.model.Note

/**
 * Created by Leyn on 14.11.2021.
 */
@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase() : RoomDatabase() {

    companion object {
        private const val DATABASE_NAME = "NoteAppDB"

        private var instance: NoteDatabase? = null

        private fun create(context: Context): NoteDatabase =
            Room.databaseBuilder(context, NoteDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()

        fun getInstance(context: Context): NoteDatabase =
            (instance ?: create(context)).also { instance = it }
    }

    abstract fun noteDao(): NoteDao
}