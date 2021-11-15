package de.leyn.noteapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import de.leyn.noteapp.db.NoteDatabase

/**
 * Created by Leyn on 15.11.2021.
 */
class MainViewModelFactory(private val db: NoteDatabase) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(db) as T
    }
}