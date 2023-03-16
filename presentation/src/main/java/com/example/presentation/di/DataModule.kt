package com.example.presentation.di

import android.content.Context
import com.example.data.repositories.NoteRepository
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    fun provideNoteRepository(context: Context): NoteRepository {
        return NoteRepository(applicationContext = context)
    }
}