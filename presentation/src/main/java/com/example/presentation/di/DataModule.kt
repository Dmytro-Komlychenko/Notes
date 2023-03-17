package com.example.presentation.di

import android.content.Context
import com.example.data.repositories.NoteDBRepository
import com.example.data.repositories.NoteEthernetRepository
import com.example.data.repositories.IsFirstLaunchAppRepository
import com.example.data.storage.sharedprefs.SharedPrefsIsFirstTimeLaunch
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    fun provideNoteDBRepository(context: Context): NoteDBRepository {
        return NoteDBRepository(applicationContext = context)
    }

    @Provides
    fun provideNoteEthernetRepository(): NoteEthernetRepository {
        return NoteEthernetRepository()
    }

    @Provides
    fun provideIsFirstLaunchAppRepository(sharedPrefsIsFirstTimeLaunch: SharedPrefsIsFirstTimeLaunch): IsFirstLaunchAppRepository {
        return IsFirstLaunchAppRepository(sharedPrefsIsFirstTimeLaunch = sharedPrefsIsFirstTimeLaunch)
    }

    @Provides
    fun provideSharedPrefsIsFirstTimeLaunch(context: Context): SharedPrefsIsFirstTimeLaunch {
        return SharedPrefsIsFirstTimeLaunch(context = context)
    }
}