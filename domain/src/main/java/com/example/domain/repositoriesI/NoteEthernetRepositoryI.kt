package com.example.domain.repositoriesI

import com.example.domain.models.Note
import com.example.domain.models.Response

interface NoteEthernetRepositoryI {
    suspend fun getNotes(): Response<List<Note>?>
}