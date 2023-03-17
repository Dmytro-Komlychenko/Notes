package com.example.presentation.screens.notelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.domain.usecases.*

class NoteListViewModelFactory(
    private val getNotesDBUseCase: GetNotesDBUseCase,
    private val saveNoteDBUseCase: SaveNoteDBUseCase,
    private val saveNotesDBUseCase: SaveNotesDBUseCase,
    private val removeNoteDBUseCase: RemoveNoteDBUseCase,
    private val getEthernetNotesUseCase: GetEthernetNotesUseCase,
    private val isFirstLaunchAppUseCase: IsFirstLaunchAppUseCase,
    private val firstLaunchAppUseCase: FirstLaunchAppUseCase,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NoteListViewModel(
            getNotesDBUseCase = getNotesDBUseCase,
            saveNoteDBUseCase = saveNoteDBUseCase,
            saveNotesDBUseCase = saveNotesDBUseCase,
            removeNoteDBUseCase = removeNoteDBUseCase,
            getEthernetNotesUseCase = getEthernetNotesUseCase,
            isFirstLaunchAppUseCase = isFirstLaunchAppUseCase,
            firstLaunchAppUseCase = firstLaunchAppUseCase,
        ) as T
    }

}