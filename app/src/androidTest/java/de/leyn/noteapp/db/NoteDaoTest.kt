package de.leyn.noteapp.db

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import de.leyn.noteapp.data.data_source.NoteDatabase
import de.leyn.noteapp.data.data_source.NoteDao
import de.leyn.noteapp.domain.model.Note
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Leyn on 02.12.2021.
 */
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class NoteDaoTest {

    private lateinit var noteDatabase: NoteDatabase
    private lateinit var dao: NoteDao

    @Before
    fun setup() {
        noteDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            NoteDatabase::class.java
        ).allowMainThreadQueries()
            .build()
        dao = noteDatabase.noteDao()
    }

    @After
    fun teardown() {
        noteDatabase.close()
    }

    @Test
    fun should_insert_note_into_database() = runBlockingTest {
        val noteBean = Note(id = 1,"Title", "Text", "01.01.01", "01.01.01")
        dao.insertNote(noteBean)

        val allNotes = dao.getAllNotes()
        assertThat(allNotes).contains(noteBean)
    }

    @Test
    fun should_delete_note_from_database() = runBlockingTest {
        val noteBean = Note(id = 1,"Title", "Text", "01.01.01", "01.01.01")
        dao.insertNote(noteBean)
        dao.deleteNote(noteBean)

        val allNotes = dao.getAllNotes()
        assertThat(allNotes).doesNotContain(noteBean)
    }
}
