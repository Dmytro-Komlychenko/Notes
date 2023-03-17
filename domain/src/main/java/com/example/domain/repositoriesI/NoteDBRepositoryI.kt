package com.example.domain.repositoriesI

import com.example.domain.models.Note

interface NoteDBRepositoryI {
    suspend fun getNotes(): List<Note>
    suspend fun insertNote(note: Note)
    suspend fun insertNotes(notes: List<Note>)
    suspend fun removeNote(id: Int)
}
