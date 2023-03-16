package com.example.presentation.di

import com.example.data.repositories.NoteRepository
import com.example.domain.usecases.GetNotesUseCase
import com.example.domain.usecases.RemoveNoteUseCase
import com.example.domain.usecases.SaveNoteUseCase
import dagger.Module
import dagger.Provides

@Module
class DomainModule {
    @Provides
    fun provideGetDataUseCase(noteRepository: NoteRepository): GetNotesUseCase{
        return GetNotesUseCase(noteRepository = noteRepository)
    }

    @Provides
    fun provideSaveDataUseCase(noteRepository: NoteRepository): SaveNoteUseCase{
        return SaveNoteUseCase(noteRepository = noteRepository)
    }

    @Provides
    fun provideRemoveDataUseCase(noteRepository: NoteRepository): RemoveNoteUseCase {
        return RemoveNoteUseCase(noteRepository = noteRepository)
    }
}