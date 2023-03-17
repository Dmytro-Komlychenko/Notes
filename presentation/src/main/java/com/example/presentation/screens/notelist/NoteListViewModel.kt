package com.example.presentation.screens.notelist

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecases.*
import com.example.presentation.mappers.NoteMapper
import com.example.presentation.mappers.ResponseMapper
import com.example.presentation.models.Note
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class NoteListViewModel(
    private val getNotesDBUseCase: GetNotesDBUseCase,
    private val saveNoteDBUseCase: SaveNoteDBUseCase,
    private val saveNotesDBUseCase: SaveNotesDBUseCase,
    private val removeNoteDBUseCase: RemoveNoteDBUseCase,
    private val getEthernetNotesUseCase: GetEthernetNotesUseCase,
    private val isFirstLaunchAppUseCase: IsFirstLaunchAppUseCase,
    private val firstLaunchAppUseCase: FirstLaunchAppUseCase,
) : ViewModel() {

    var notes: MutableLiveData<MutableList<Note>> = MutableLiveData()
    private val noteMapper = NoteMapper()
    private val responseMapper = ResponseMapper()
    var toAdd: Boolean = false
    var removePosition: Int? = null
    val isInternetConnection: MutableLiveData<Boolean> = MutableLiveData()
    private var loadJob: Job? = null
    var isFirstLaunchApp: Boolean? = null

    init {
        loadJob = setLoadJob()
    }

    fun stopLoading() {
        loadJob?.cancel()
        loadJob = null
    }

    fun restartLoading() {
        loadJob = setLoadJob()
    }


    private fun setLoadJob(): Job {
        return viewModelScope.launch {
            isFirstLaunchApp = isFirstLaunchAppUseCase.execute()
            if (isFirstLaunchApp!!)
                setFirstLoadJob()
            else
                setNotFirstLoadJob()
        }
    }

    private suspend fun setFirstLoadJob() {
        Log.i("lifecycle", "setFirstLoadJob")
        firstLaunchAppUseCase.execute()
        if (!notes.isInitialized) {
            notes.value =
                responseMapper.mapFromDomain(getEthernetNotesUseCase.execute()).data?.toMutableList()
            if (notes.value!!.isNotEmpty())
                saveNotesDBUseCase.execute(noteMapper.mapToDomain(notes.value!!))
        }
    }

    private suspend fun setNotFirstLoadJob() {
        Log.i("lifecycle", "setNotFirstLoadJob")
        notes.value = noteMapper.mapFromDomain(getNotesDBUseCase.execute())

        val ethernetNotes =
            responseMapper.mapFromDomain(getEthernetNotesUseCase.execute()).data?.toMutableList()
                ?: mutableListOf()

        val result = mutableListOf<Note>()
        if (ethernetNotes != notes.value) {
            ethernetNotes.forEach {
                if (!notes.value?.contains(it)!!)
                    result.add(it)
            }
            notes.value?.forEach {
                if (!ethernetNotes.contains(it) && !result.contains(it))
                    result.add(it)
            }
            notes.value = result
            saveNotesDBUseCase.execute(noteMapper.mapToDomain(notes.value!!))
        }
    }

    fun addNote(note: Note) {
        toAdd = true
        viewModelScope.launch {
            saveNoteDBUseCase.execute(noteMapper.mapToDomain(note))
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
            removeNoteDBUseCase.execute(note.id)
        }

    }
}