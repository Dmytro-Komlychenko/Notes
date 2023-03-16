package com.example.presentation.screens.notelist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecases.GetNotesUseCase
import com.example.domain.usecases.RemoveNoteUseCase
import com.example.domain.usecases.SaveNoteUseCase
import com.example.presentation.mappers.NoteMapper
import com.example.presentation.models.Note
import kotlinx.coroutines.launch

class NoteListViewModel(
    private val getNotesUseCase: GetNotesUseCase,
    private val saveNoteUseCase: SaveNoteUseCase,
    private val removeNoteUseCase: RemoveNoteUseCase,
) : ViewModel() {

    var notes: MutableLiveData<MutableList<Note>> = MutableLiveData()
    private val noteMapper: NoteMapper = NoteMapper()
    var toAdd: Boolean = false
    var removePosition: Int? = null

    init {
        viewModelScope.launch {
            notes.value = noteMapper.mapFromDomain(getNotesUseCase.execute())
        }
    }

    fun addNote(note: Note) {
        toAdd = true
        viewModelScope.launch {
            saveNoteUseCase.execute(noteMapper.mapToDomain(note))
        }
        val oldValue = notes.value
        oldValue?.add(0, note)
        notes.value = oldValue!!
    }

    fun removeNote(position: Int) {
        removePosition = position
        val note = notes.value!![position]
        val oldValue = notes.value
        oldValue?.remove(oldValue.removeAt(position))
        notes.value = oldValue!!
        viewModelScope.launch {
            removeNoteUseCase.execute(note.id)
        }

    }
}