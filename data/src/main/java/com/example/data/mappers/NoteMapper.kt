package com.example.data.mappers

import com.example.data.storage.entities.NoteEntity
import com.example.domain.models.Note

class NoteMapper {

    private fun mapFromEntity(noteEntity: NoteEntity): Note {
        return Note(
            id = noteEntity.id,
            title = noteEntity.title,
            description = noteEntity.description,
            dateOfChange = noteEntity.dateOfChange,
        )
    }

    fun mapFromEntity(noteEntities: List<NoteEntity>): List<Note> {
        val notes = mutableListOf<Note>()
        noteEntities.forEach { note -> notes.add(mapFromEntity(note)) }
        return notes
    }

    fun mapToEntity(note: Note): NoteEntity {
        return NoteEntity(
            id = note.id,
            title = note.title,
            description = note.description,
            dateOfChange = note.dateOfChange,
        )
    }
}