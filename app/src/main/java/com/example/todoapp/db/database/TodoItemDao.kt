package com.example.todoapp.db.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.todoapp.db.database.entities.TodoDbEntity

@Dao
interface TodoItemDao {

    @Insert(entity = TodoDbEntity::class)
    fun insertNewTodoItemData(todo: TodoDbEntity)

    @Query("SELECT todo.id, text, importance_name, deadline, done, createdAt, changedAt FROM todo\n" +
            "INNER JOIN importance_levels ON todo.importance_id = importance_levels.id")
    fun getAllTodoData(): List<TodoItemInfoTuple>

    @Query("DELETE FROM todo WHERE id = :todoId")
    fun deleteTodoDataById(todoId: Int)
}