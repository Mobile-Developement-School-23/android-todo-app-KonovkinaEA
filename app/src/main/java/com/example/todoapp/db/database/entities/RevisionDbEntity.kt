package com.example.todoapp.db.database.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "revision",
    indices = [Index("id")]
)
data class RevisionDbEntity(
    @PrimaryKey val id: Int,
    var revision: Int
)
