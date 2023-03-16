package com.example.data.storage.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_table")
data class NoteEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var title: String,
    var description: String,
    var dateOfChange: String,
)