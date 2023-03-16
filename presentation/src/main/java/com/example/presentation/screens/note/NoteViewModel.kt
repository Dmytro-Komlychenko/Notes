package com.example.presentation.screens.note

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.presentation.models.Note

class NoteViewModel: ViewModel() {

    var note: MutableLiveData<Note> = MutableLiveData(Note(0, "Title 1", "Description 1", "15.03.2023"))
    var wasFragmentShown = false
    //var wasNoteLoaded = false
}