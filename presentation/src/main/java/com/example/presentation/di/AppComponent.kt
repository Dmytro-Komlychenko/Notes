package com.example.presentation.di

import com.example.presentation.screens.main.MainActivity
import com.example.presentation.screens.note.NoteFragment
import dagger.Component

@Component(modules = [PresentationModule::class, DomainModule::class, DataModule::class])
interface AppComponent {
    fun inject(noteFragment: NoteFragment)
    fun inject(mainActivity: MainActivity)

}