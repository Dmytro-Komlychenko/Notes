package com.example.presentation.screens.note

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.domain.usecases.SaveNoteDBUseCase

class NoteViewModelFactory(
    private val saveNoteDBUseCase: SaveNoteDBUseCase,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NoteViewModel(
            saveNoteDBUseCase = saveNoteDBUseCase,
        ) as T
    }

}