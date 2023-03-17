package com.example.data.repositories

import android.content.Context
import com.example.data.mappers.NoteMapper
import com.example.data.storage.dao.NoteDao
import com.example.data.storage.database.NoteDB
import com.example.domain.models.Note
import com.example.domain.repositoriesI.NoteDBRepositoryI

class NoteDBRepository(applicationContext: Context) : NoteDBRepositoryI {

    private val noteMapper = NoteMapper()

    private var noteDao: NoteDao =
        NoteDB.getDB(applicationContext).noteDao()


    override suspend fun getNotes(): List<Note> {
        return noteMapper.mapFromEntity(noteDao.getAllNotes())
    }


    override suspend fun insertNote(note: Note) {
        noteDao.insertNote(noteMapper.mapToEntity(note))
    }

    override suspend fun insertNotes(notes: List<Note>) {
        val res = noteMapper.mapToEntity(notes)
        noteDao.insertNotes(res)
    }

    override suspend fun removeNote(id: Int) {
        noteDao.removeNoteById(id)
    }

}