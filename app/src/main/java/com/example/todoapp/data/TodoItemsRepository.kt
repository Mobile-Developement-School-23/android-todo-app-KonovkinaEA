package com.example.todoapp.data

import com.example.todoapp.data.item.TodoItem
import kotlinx.coroutines.flow.Flow

interface TodoItemsRepository {
    fun todoItems(): Flow<List<TodoItem>>
    suspend fun getTodoItem(id: String): TodoItem?
    suspend fun addTodoItem(todoItem: TodoItem)
    suspend fun updateTodoItem(todoItem: TodoItem)
    suspend fun removeTodoItem(id: String)
    suspend fun removeTodoItemAt(index: Int)
}