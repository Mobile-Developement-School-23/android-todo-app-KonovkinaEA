package com.example.todoapp.db.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "todo",
    indices = [Index("id")],
    foreignKeys = [
        ForeignKey(
            entity = ImportanceLevelsDbEntity::class,
            parentColumns = ["id"],
            childColumns = ["importance_id"]
        )
    ]
)
data class TodoDbEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    var text: String,
    @ColumnInfo(name = "importance_id") var importanceId: Int,
    @ColumnInfo(defaultValue = "NULL") var deadline: Int? = null,
    @ColumnInfo(defaultValue = "0") var done: Boolean = false,
    val createdAt: Int,
    var changedAt: Int
)
