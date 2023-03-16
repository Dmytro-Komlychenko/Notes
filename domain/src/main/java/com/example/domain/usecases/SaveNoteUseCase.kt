package com.example.domain.usecases

import com.example.domain.models.Note
import com.example.domain.repositoriesI.NoteRepositoryI

class SaveNoteUseCase(private val noteRepository: NoteRepositoryI) {
    suspend fun execute(note: Note) {
        noteRepository.insertNote(note)
    }
}