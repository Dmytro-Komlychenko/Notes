package com.example.domain.usecases

import com.example.domain.repositoriesI.NoteRepositoryI

class RemoveNoteUseCase(private val noteRepository: NoteRepositoryI) {
    suspend fun execute(id: Int) {
        return noteRepository.removeNote(id)
    }
}