package com.example.todoapp.data

import androidx.lifecycle.MutableLiveData
import com.example.todoapp.data.item.TodoItem
import kotlinx.coroutines.flow.Flow

interface TodoItemsRepository {
    suspend fun todoItems(): Flow<List<TodoItem>>
    suspend fun getTodoItem(id: String): TodoItem?
    suspend fun addTodoItem(todoItem: TodoItem)
    suspend fun updateTodoItem(todoItem: TodoItem)
    suspend fun removeTodoItem(id: String)
    suspend fun loadDataFromServer()
    suspend fun loadDataFromDB()
    fun reloadData()
    fun errorListLiveData(): MutableLiveData<Boolean>
    fun errorItemLiveData(): MutableLiveData<Boolean>
}
