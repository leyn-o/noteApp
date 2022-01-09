package de.leyn.noteapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import de.leyn.noteapp.db.NoteDataSource

/**
 * Created by Leyn on 15.11.2021.
 */
class ViewModelFactory(private val dataSource: NoteDataSource) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NoteViewModel(dataSource) as T
    }
}