package com.example.todoapp.data

import com.example.todoapp.data.item.TodoItem
import com.example.todoapp.data.db.TodoItemDao
import com.example.todoapp.utils.createTodo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext

class Repository(private val todoItemDao: TodoItemDao) : TodoItemsRepository {
    private val todoItems: MutableList<TodoItem> = mutableListOf()
    private val todoItemsFlow: MutableStateFlow<List<TodoItem>> = MutableStateFlow(mutableListOf())

    suspend fun setTodoItems() {
        withContext(Dispatchers.IO) {
            val todoItemDaoList = todoItemDao.getAllTodoData()
            todoItems.clear()
            todoItems.addAll(todoItemDaoList.map { it.toTodoItem() })
            updateFlow()
        }
    }

    override suspend fun todoItems(): Flow<List<TodoItem>> = todoItemsFlow.asStateFlow()

    override suspend fun getTodoItem(id: String): TodoItem? = todoItems.firstOrNull { it.id == id }

    override suspend fun addTodoItem(todoItem: TodoItem) =
        withContext(Dispatchers.IO) {
            val newTodo = createTodo(todoItem)
            todoItemDao.insertNewTodoItemData(newTodo.toTodoDbEntity())
            todoItems.add(todoItem)
            updateFlow()
        }

    override suspend fun updateTodoItem(todoItem: TodoItem) =
        withContext(Dispatchers.IO) {
            val index = todoItems.indexOfFirst { it.id == todoItem.id }
            if (index != -1) {
                val updatedTodo = createTodo(todoItem)
                todoItemDao.updateTodoData(updatedTodo.toTodoDbEntity())
                todoItems[index] = todoItem
                updateFlow()
            }
        }

    override suspend fun removeTodoItem(id: String) =
        withContext(Dispatchers.IO) {
            val index = todoItems.indexOfFirst { it.id == id }
            if (index != -1) {
                todoItemDao.deleteTodoDataById(id)
                todoItems.removeAt(index)
                updateFlow()
            }
        }

    private fun updateFlow() {
        todoItemsFlow.update {
            todoItems.toList()
        }
    }
}