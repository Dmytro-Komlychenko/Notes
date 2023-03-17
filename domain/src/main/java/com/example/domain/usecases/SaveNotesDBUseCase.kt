package com.example.domain.usecases

import com.example.domain.models.Note
import com.example.domain.repositoriesI.NoteDBRepositoryI

class SaveNotesDBUseCase(private val noteRepository: NoteDBRepositoryI) {
    suspend fun execute(notes: List<Note>) {
        noteRepository.insertNotes(notes)
    }
}