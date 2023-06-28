package com.example.todoapp.data

import androidx.lifecycle.MutableLiveData
import com.example.todoapp.data.api.Common
import com.example.todoapp.data.api.model.TodoListServer
import com.example.todoapp.data.db.RevisionDao
import com.example.todoapp.data.item.TodoItem
import com.example.todoapp.data.db.TodoItemDao
import com.example.todoapp.utils.createTodo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository(
    private val todoItemDao: TodoItemDao,
    private val revisionDao: RevisionDao
) : TodoItemsRepository {
    private val todoItems: MutableList<TodoItem> = mutableListOf()
    private val todoItemsFlow: MutableStateFlow<List<TodoItem>> = MutableStateFlow(mutableListOf())

    private lateinit var todoItemsFromServer: List<TodoItem>
    private var revisionFromServer: Long = -1

    val errorLiveData = MutableLiveData<String>()

    suspend fun setTodoItems() {
        withContext(Dispatchers.IO) {
            val dataWasUpdated = updateDataFromServer()

            if (!dataWasUpdated) {
                val todoItemDaoList = todoItemDao.getAllTodoData()

                todoItems.clear()
                todoItems.addAll(todoItemDaoList.map { it.toTodoItem() })
                updateFlow()
            }
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

    private suspend fun updateDataFromServer(): Boolean =
        withContext(Dispatchers.IO) {
            var dataWasUpdated = false
            val response = Common.apiService.getAllTodoData()

            if (response.isSuccessful) {
                val dataFromServer = response.body() as TodoListServer

                if (dataFromServer.todoItems != null) {
                    todoItemsFromServer = dataFromServer.todoItems.map {
                        it.toTodoItem()
                    }
                }

                revisionFromServer = dataFromServer.revision!!
                if (revisionFromServer > revisionDao.getCurrentRevision()) {
                    dataWasUpdated = true
                    revisionDao.updateRevision(revisionFromServer)
                    todoItems.clear()
                    todoItems.addAll(todoItemsFromServer)
                    updateFlow()
                    updateDatabase()
                }
            } else {
                errorLiveData.postValue(response.code().toString())
            }

            return@withContext dataWasUpdated
        }

    private fun updateDatabase() {
        val todoDbList = todoItems.map { createTodo(it).toTodoDbEntity() }
        todoItemDao.replaceAllTodoItems(todoDbList)
    }

    private fun updateFlow() {
        todoItemsFlow.update {
            todoItems.toList()
        }
    }
}