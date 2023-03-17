package com.example.data.mappers

import com.example.data.storage.entities.NoteEntity
import com.example.domain.models.Note
import retrofit2.Response

class ResponseMapper {

    private val noteMapper = NoteMapper()

    fun mapFromEntity(response: Response<List<NoteEntity>?>): com.example.domain.models.Response<List<Note>?> {
        val body = response.body().let {
            noteMapper.mapFromEntity(response.body())
        }

        val error = response.errorBody()?.string()
        return com.example.domain.models.Response(response.code(), body, error)
    }
}