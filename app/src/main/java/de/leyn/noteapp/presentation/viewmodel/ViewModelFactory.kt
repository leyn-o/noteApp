package de.leyn.noteapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import de.leyn.noteapp.data.repositories.NoteRepository

/**
 * Created by Leyn on 15.11.2021.
 */
class ViewModelFactory(private val repository: NoteRepository) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NoteViewModel(repository) as T
    }
}