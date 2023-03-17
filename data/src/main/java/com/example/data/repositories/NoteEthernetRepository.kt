package com.example.data.repositories

import com.example.data.mappers.ResponseMapper
import com.example.data.network.retrofit.RetrofitInstance
import com.example.domain.models.Note
import com.example.domain.repositoriesI.NoteEthernetRepositoryI
import kotlinx.coroutines.delay

class NoteEthernetRepository : NoteEthernetRepositoryI {

    private val responseMapper = ResponseMapper()

    override suspend fun getNotes(): com.example.domain.models.Response<List<Note>?> {
        delay(5000)
        return responseMapper.mapFromEntity(RetrofitInstance.api.getNotes())
    }
}