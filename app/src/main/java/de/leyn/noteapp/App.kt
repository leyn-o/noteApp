package de.leyn.noteapp

import android.app.Application
import androidx.room.Room
import de.leyn.noteapp.db.NoteDatabase

/**
 * Created by Leyn on 14.11.2021.
 */
class App : Application() {

    private lateinit var db: NoteDatabase

    companion object {
        const val INTENT_NOTE = "Note"
    }

    override fun onCreate() {
        super.onCreate()

        initDatabase()
    }

    private fun initDatabase() {
        db = Room.databaseBuilder(applicationContext, NoteDatabase::class.java, "NoteAppDB")
            .build()
    }

    fun getDB(): NoteDatabase {
        return db
    }
}