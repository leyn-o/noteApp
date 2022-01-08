package de.leyn.noteapp.db

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
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

    private lateinit var database: NoteDatabase
    private lateinit var dao: NoteDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            NoteDatabase::class.java
        ).allowMainThreadQueries()
            .build()
        dao = database.noteDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertShoppingItem() = runBlockingTest {
        val noteBean = NoteBean(id = 1,"Title", "Text", "01.01.01", "01.01.01")
        dao.insertNote(noteBean)

        val allNotes = dao.getAllNotes()
        assertThat(allNotes).contains(noteBean)
    }

    @Test
    fun deleteShoppingItem() = runBlockingTest {
        val noteBean = NoteBean(id = 1,"Title", "Text", "01.01.01", "01.01.01")
        dao.insertNote(noteBean)
        dao.deleteNote(noteBean)

        val allNotes = dao.getAllNotes()
        assertThat(allNotes).doesNotContain(noteBean)
    }
}
