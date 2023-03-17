package com.example.presentation.screens.note

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecases.SaveNoteDBUseCase
import com.example.presentation.mappers.NoteMapper
import com.example.presentation.models.Note
import kotlinx.coroutines.launch

class NoteViewModel(
    private val saveNoteDBUseCase: SaveNoteDBUseCase
) : ViewModel() {

    private val mapper: NoteMapper = NoteMapper()
    var note: MutableLiveData<Note> = MutableLiveData()
    var wasFragmentShown = false

    fun saveNote() {
        viewModelScope.launch {
            saveNoteDBUseCase.execute(mapper.mapToDomain(note.value!!))
        }
    }
}