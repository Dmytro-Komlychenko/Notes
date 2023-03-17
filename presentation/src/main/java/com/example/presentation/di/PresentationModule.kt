package com.example.presentation.di

import android.content.Context
import com.example.domain.usecases.*
import com.example.presentation.screens.note.NoteViewModelFactory
import com.example.presentation.screens.notelist.NoteListViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class PresentationModule(val context: Context) {

    @Provides
    fun provideContext(): Context {
        return context
    }

    @Provides
    fun provideNoteListViewModelFactory(
        getNotesDBUseCase: GetNotesDBUseCase,
        saveNoteDBUseCase: SaveNoteDBUseCase,
        saveNotesDBUseCase: SaveNotesDBUseCase,
        removeNoteDBUseCase: RemoveNoteDBUseCase,
        getEthernetNotesUseCase: GetEthernetNotesUseCase,
        isFirstLaunchAppUseCase: IsFirstLaunchAppUseCase,
        firstLaunchAppUseCase: FirstLaunchAppUseCase,
    ): NoteListViewModelFactory {
        return NoteListViewModelFactory(
            getNotesDBUseCase = getNotesDBUseCase,
            saveNoteDBUseCase = saveNoteDBUseCase,
            saveNotesDBUseCase = saveNotesDBUseCase,
            removeNoteDBUseCase = removeNoteDBUseCase,
            getEthernetNotesUseCase = getEthernetNotesUseCase,
            isFirstLaunchAppUseCase = isFirstLaunchAppUseCase,
            firstLaunchAppUseCase = firstLaunchAppUseCase,
        )
    }

    @Provides
    fun provideNoteViewModelFactory(
        saveNoteDBUseCase: SaveNoteDBUseCase,
    ): NoteViewModelFactory {
        return NoteViewModelFactory(
            saveNoteDBUseCase = saveNoteDBUseCase,
        )
    }
}