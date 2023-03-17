package com.example.domain.usecases

import com.example.domain.models.Note
import com.example.domain.repositoriesI.NoteDBRepositoryI

class SaveNoteDBUseCase(private val noteRepository: NoteDBRepositoryI) {
    suspend fun execute(note: Note) {
        noteRepository.insertNote(note)
    }
}