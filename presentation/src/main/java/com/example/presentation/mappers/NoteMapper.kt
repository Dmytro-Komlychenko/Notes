package com.example.presentation.mappers

import androidx.lifecycle.MutableLiveData
import com.example.presentation.models.DateTime
import com.example.presentation.models.Note

class NoteMapper {

    private fun mapFromDomain(noteEntity: com.example.domain.models.Note): Note {
        return Note(
            id = noteEntity.id,
            title = noteEntity.title,
            description = noteEntity.description,
            dateOfChange = DateTime(noteEntity.dateOfChange),
        )
    }

    fun mapFromDomain(noteEntities: List<com.example.domain.models.Note>?): MutableList<Note> {
        val notes = mutableListOf<Note>()
        noteEntities?.forEach { note -> notes.add(mapFromDomain(note)) }
        return notes
    }

    fun mapToDomain(note: Note): com.example.domain.models.Note {
        return com.example.domain.models.Note(
            id = note.id,
            title = note.title,
            description = note.description,
            dateOfChange = note.dateOfChange.value,
        )
    }

    fun mapToDomain(notes: List<Note>): List<com.example.domain.models.Note> {
        val notesDomain = mutableListOf<com.example.domain.models.Note>()
        notes.forEach { note -> notesDomain.add(mapToDomain(note)) }
        return notesDomain
    }
}