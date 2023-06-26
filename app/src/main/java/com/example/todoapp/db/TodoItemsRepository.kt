package com.example.todoapp.db

import com.example.todoapp.data.TodoItemsRepository
import com.example.todoapp.data.item.TodoItem
import com.example.todoapp.db.database.RevisionDao
import com.example.todoapp.db.database.TodoItemDao
import com.example.todoapp.db.database.TodoItemInfoTuple
import com.example.todoapp.db.database.entities.Todo
import com.example.todoapp.utils.dateToUnix
import com.example.todoapp.utils.getImportanceId
import com.example.todoapp.utils.stringToImportance
import com.example.todoapp.utils.unixToDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import java.util.Date

class TodoItemsRepository(
    private val todoItemDao: TodoItemDao,
    private val revisionDao: RevisionDao
) : TodoItemsRepository {

    override suspend fun todoItems(): Flow<List<TodoItem>> =
        withContext(Dispatchers.IO) {
            val todoItems: MutableList<TodoItem> = mutableListOf()
            val todoItemDaoList = todoItemDao.getAllTodoData()

            if (todoItemDaoList.isNotEmpty()) {
                todoItemDaoList.forEach {
                    todoItems.add(convertToTodoItem(it))
                }
            }

            return@withContext MutableStateFlow(todoItems.toList()).asStateFlow()
        }

    override suspend fun getTodoItem(id: String): TodoItem? =
        withContext(Dispatchers.IO) {
            val todoItemInfoTuple = todoItemDao.getTodoDataById(id.toLong())
            return@withContext if (todoItemInfoTuple != null) convertToTodoItem(todoItemInfoTuple) else null
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
            val todoId = id.toLong()
            todoItemDao.deleteTodoDataById(todoId)
        }

    suspend fun getRevision(): Int =
        withContext(Dispatchers.IO) {
            return@withContext revisionDao.getCurrentRevision()
        }

    suspend fun updateRevision(newRevision: Int) =
        withContext(Dispatchers.IO) {
            revisionDao.updateRevision(newRevision)
        }

    private fun convertToTodoItem(todoItemInfoTuple: TodoItemInfoTuple): TodoItem {
        return TodoItem(
            id = todoItemInfoTuple.id.toString(),
            text = todoItemInfoTuple.text,
            importance = stringToImportance(todoItemInfoTuple.importance),
            deadline = unixToDate(todoItemInfoTuple.deadline),
            isDone = todoItemInfoTuple.done,
            creationDate = Date(todoItemInfoTuple.createdAt * 1000L),
            modificationDate = Date(todoItemInfoTuple.changedAt * 1000L)
        )
    }

    private fun createTodo(todoItem: TodoItem): Todo {
        return Todo(
            id = todoItem.id.toLong(),
            text = todoItem.text,
            importanceId = getImportanceId(todoItem.importance),
            deadline = dateToUnix(todoItem.deadline),
            done = todoItem.isDone,
            createdAt = todoItem.creationDate.time / 1000,
            changedAt = todoItem.modificationDate.time / 1000
        )
    }
}