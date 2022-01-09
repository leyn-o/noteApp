package de.leyn.noteapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Created by Leyn on 14.11.2021.
 */
@Database(entities = [NoteBean::class], version = 1)
abstract class DatabaseService() : RoomDatabase() {

    companion object {
        private const val DATABASE_NAME = "NoteAppDB"

        private var instance: DatabaseService? = null

        private fun create(context: Context): DatabaseService =
            Room.databaseBuilder(context, DatabaseService::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()

        fun getInstance(context: Context): DatabaseService =
            (instance ?: create(context)).also { instance = it }
    }

    abstract fun noteDao(): NoteDao
}