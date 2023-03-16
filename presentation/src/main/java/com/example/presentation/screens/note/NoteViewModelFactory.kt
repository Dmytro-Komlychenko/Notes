package com.example.presentation.screens.note

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.domain.usecases.SaveNoteUseCase

class NoteViewModelFactory(
    private val saveNoteUseCase: SaveNoteUseCase,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NoteViewModel(
            saveNoteUseCase = saveNoteUseCase,
        ) as T
    }

}