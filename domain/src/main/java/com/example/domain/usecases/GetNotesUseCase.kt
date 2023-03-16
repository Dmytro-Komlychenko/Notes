package com.example.domain.usecases

import com.example.domain.models.Note
import com.example.domain.repositoriesI.NoteRepositoryI

class GetNotesUseCase(private val noteRepository: NoteRepositoryI) {
    suspend fun execute(): List<Note> {
        return noteRepository.getNotes()
    }
}