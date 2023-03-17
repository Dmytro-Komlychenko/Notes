package com.example.presentation.mappers

import com.example.presentation.models.Note
import com.example.presentation.models.Response

class ResponseMapper {

    private val noteMapper = NoteMapper()

    fun mapFromDomain(response: com.example.domain.models.Response<List<com.example.domain.models.Note>?>): Response<List<Note>?> {
        val body = response.data.let {
            noteMapper.mapFromDomain(response.data)
        }

        val error = response.errorMessage
        return Response(response.statusCode, body, error)
    }
}