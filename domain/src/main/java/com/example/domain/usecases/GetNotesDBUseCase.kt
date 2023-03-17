package com.example.domain.usecases

import com.example.domain.models.Note
import com.example.domain.repositoriesI.NoteDBRepositoryI

class GetNotesDBUseCase(private val noteRepository: NoteDBRepositoryI) {
    suspend fun execute(): List<Note> {
        return noteRepository.getNotes()
    }
}