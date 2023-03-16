package com.example.data.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.storage.entities.NoteEntity

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: NoteEntity)

    @Query("SELECT * FROM note_table")
    suspend fun getAllNotes(): List<NoteEntity>

    @Query("DELETE FROM note_table WHERE id = :noteId")
    suspend fun removeNoteById(noteId: Int)
}