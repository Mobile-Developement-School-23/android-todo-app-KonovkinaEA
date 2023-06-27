package com.example.todoapp.data.db

import com.example.todoapp.data.TodoItemsRepository
import com.example.todoapp.data.item.TodoItem
import com.example.todoapp.data.db.database.TodoItemDao
import com.example.todoapp.utils.createTodo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext

class TodoItemsDbRepository(private val todoItemDao: TodoItemDao) : TodoItemsRepository {

    override suspend fun todoItems(): Flow<List<TodoItem>> =
        withContext(Dispatchers.IO) {
            val todoItems: MutableList<TodoItem> = mutableListOf()
            val todoItemDaoList = todoItemDao.getAllTodoData()

            if (todoItemDaoList.isNotEmpty()) {
                todoItemDaoList.forEach {
                    todoItems.add(it.toTodoItem())
                }
            }

            return@withContext MutableStateFlow(todoItems.toList()).asStateFlow()
        }

    override suspend fun getTodoItem(id: String): TodoItem? =
        withContext(Dispatchers.IO) {
            return@withContext todoItemDao.getTodoDataById(id.toLong())?.toTodoItem()
        }

    override suspend fun addTodoItem(todoItem: TodoItem) =
        withContext(Dispatchers.IO) {
            val newTodo = createTodo(todoItem)
            todoItemDao.insertNewTodoItemData(newTodo.toTodoDbEntity())
        }

    override suspend fun updateTodoItem(todoItem: TodoItem) =
        withContext(Dispatchers.IO) {
            val updatedTodo = createTodo(todoItem)
            todoItemDao.updateTodoData(updatedTodo.toTodoDbEntity())
        }

    override suspend fun removeTodoItem(id: String) =
        withContext(Dispatchers.IO) {
            todoItemDao.deleteTodoDataById(id.toLong())
        }
}