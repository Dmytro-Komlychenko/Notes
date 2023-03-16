package com.example.presentation.screens.notelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.domain.usecases.GetNotesUseCase
import com.example.domain.usecases.RemoveNoteUseCase
import com.example.domain.usecases.SaveNoteUseCase

class NoteListViewModelFactory(
    private val getNotesUseCase: GetNotesUseCase,
    private val saveNoteUseCase: SaveNoteUseCase,
    private val removeNoteUseCase: RemoveNoteUseCase,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NoteListViewModel(
            getNotesUseCase = getNotesUseCase,
            saveNoteUseCase = saveNoteUseCase,
            removeNoteUseCase = removeNoteUseCase,
        ) as T
    }

}