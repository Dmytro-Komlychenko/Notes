package com.example.presentation.di

import com.example.data.repositories.IsFirstLaunchAppRepository
import com.example.data.repositories.NoteDBRepository
import com.example.data.repositories.NoteEthernetRepository
import com.example.domain.usecases.*
import dagger.Module
import dagger.Provides

@Module
class DomainModule {
    @Provides
    fun provideGetNoteUseCase(noteDBRepository: NoteDBRepository): GetNotesDBUseCase{
        return GetNotesDBUseCase(noteRepository = noteDBRepository)
    }

    @Provides
    fun provideSaveNoteDBUseCase(noteDBRepository: NoteDBRepository): SaveNoteDBUseCase{
        return SaveNoteDBUseCase(noteRepository = noteDBRepository)
    }

    @Provides
    fun provideSaveNotesDBUseCase(noteDBRepository: NoteDBRepository): SaveNotesDBUseCase {
        return SaveNotesDBUseCase(noteRepository = noteDBRepository)
    }

    @Provides
    fun provideRemoveNoteDBUseCase(noteDBRepository: NoteDBRepository): RemoveNoteDBUseCase {
        return RemoveNoteDBUseCase(noteRepository = noteDBRepository)
    }

    @Provides
    fun provideGetEthernetNoteUseCase(noteEthernetRepository: NoteEthernetRepository): GetEthernetNotesUseCase{
        return GetEthernetNotesUseCase(noteRepository = noteEthernetRepository)
    }

    @Provides
    fun provideIsFirstLaunchAppUseCase(isFirstLaunchAppRepository: IsFirstLaunchAppRepository): IsFirstLaunchAppUseCase{
        return IsFirstLaunchAppUseCase(isFirstLaunchAppRepository = isFirstLaunchAppRepository)
    }

    @Provides
    fun provideFirstLaunchAppUseCase(isFirstLaunchAppRepository: IsFirstLaunchAppRepository): FirstLaunchAppUseCase{
        return FirstLaunchAppUseCase(isFirstLaunchAppRepository = isFirstLaunchAppRepository)
    }
}