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
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "importance_id") var importanceId: Int,
    var text: String,
    /*@ColumnInfo(defaultValue = "NULL")*/ var deadline: Long?/* = null*/,
    /*@ColumnInfo(defaultValue = "0")*/ var done: Boolean/* = false*/,
    val createdAt: Long,
    var changedAt: Long
)
