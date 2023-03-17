package com.example.domain.usecases

import com.example.domain.repositoriesI.NoteDBRepositoryI

class RemoveNoteDBUseCase(private val noteRepository: NoteDBRepositoryI) {
    suspend fun execute(id: Int) {
        return noteRepository.removeNote(id)
    }
}