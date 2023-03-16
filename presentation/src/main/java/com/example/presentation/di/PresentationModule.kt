package com.example.presentation.di

import android.content.Context
import com.example.domain.usecases.GetNotesUseCase
import com.example.domain.usecases.RemoveNoteUseCase
import com.example.domain.usecases.SaveNoteUseCase
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
        getNotesUseCase: GetNotesUseCase,
        saveNoteUseCase: SaveNoteUseCase,
        removeNoteUseCase: RemoveNoteUseCase,
    ): NoteListViewModelFactory {
        return NoteListViewModelFactory(
            getNotesUseCase = getNotesUseCase,
            saveNoteUseCase = saveNoteUseCase,
            removeNoteUseCase = removeNoteUseCase,
        )
    }

    @Provides
    fun provideNoteViewModelFactory(
        saveNoteUseCase: SaveNoteUseCase,
    ): NoteViewModelFactory {
        return NoteViewModelFactory(
            saveNoteUseCase = saveNoteUseCase,
        )
    }
}