package com.example.domain.repositoriesI

import com.example.domain.models.Note

interface NoteRepositoryI {
    suspend fun getNotes(): List<Note>
    suspend fun insertNote(note: Note)
    suspend fun removeNote(id: Int)
}
