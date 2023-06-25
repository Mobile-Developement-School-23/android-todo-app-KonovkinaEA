package com.example.todoapp.db.database.entities

data class Todo(
    val id: Int,
    var text: String,
    var importanceId: Int,
    var deadline: Int?,
    var done: Boolean,
    val createdAt: Int,
    var changedAt: Int
) {

    fun toTodoDbEntity(): TodoDbEntity = TodoDbEntity(
        id = 0,
        text = text,
        importanceId = importanceId,
        deadline = deadline,
        done = done,
        createdAt = createdAt,
        changedAt = changedAt
    )
}
