package com.example.domain.usecases

import com.example.domain.models.Note
import com.example.domain.models.Response
import com.example.domain.repositoriesI.NoteEthernetRepositoryI

class GetEthernetNotesUseCase(private val noteRepository: NoteEthernetRepositoryI) {
    suspend fun execute(): Response<List<Note>?> {
        return noteRepository.getNotes()
    }
}