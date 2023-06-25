package com.example.todoapp.db

import com.example.todoapp.db.database.TodoItemDao
import com.example.todoapp.db.database.TodoItemInfoTuple
import com.example.todoapp.db.database.entities.TodoDbEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TodoRepository(private val todoItemDao: TodoItemDao) {

    suspend fun insertNewTodoItemData(todoDbEntity: TodoDbEntity) {
        withContext(Dispatchers.IO) {
            todoItemDao.insertNewTodoItemData(todoDbEntity)
        }
    }

    suspend fun getAllTodoData(): List<TodoItemInfoTuple> {
        return withContext(Dispatchers.IO) {
            return@withContext todoItemDao.getAllTodoData()
        }
    }

    suspend fun deleteTodoDataById(id: Int) {
        withContext(Dispatchers.IO) {
            todoItemDao.deleteTodoDataById(id)
        }
    }
}