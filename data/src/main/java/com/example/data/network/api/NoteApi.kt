package com.example.data.network.api

import com.example.data.storage.entities.NoteEntity
import retrofit2.Response
import retrofit2.http.GET

interface NoteApi {
    @GET("v1/notes")
    suspend fun getNotes(): Response<List<NoteEntity>?>
}