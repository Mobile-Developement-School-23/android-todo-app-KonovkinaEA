package com.example.todoapp.data

import androidx.lifecycle.MutableLiveData
import com.example.todoapp.data.api.Common
import com.example.todoapp.data.api.model.ItemContainer
import com.example.todoapp.data.api.model.ItemResponse
import com.example.todoapp.data.api.model.TodoListResponse
import com.example.todoapp.data.db.RevisionDao
import com.example.todoapp.data.item.TodoItem
import com.example.todoapp.data.db.TodoItemDao
import com.example.todoapp.utils.createTodo
import com.example.todoapp.utils.toTodoItemServer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import retrofit2.Response

class Repository(
    private val todoItemDao: TodoItemDao,
    private val revisionDao: RevisionDao
) : TodoItemsRepository {
    private val todoItems: MutableList<TodoItem> = mutableListOf()
    private val todoItemsFlow: MutableStateFlow<List<TodoItem>> = MutableStateFlow(mutableListOf())

    val errorLoadLiveData = MutableLiveData<Boolean>()

    suspend fun setTodoItems(firstLoad: Boolean) {
        withContext(Dispatchers.IO) {
            val dataWasUpdated = updateDataFromServer()

            if (!dataWasUpdated && firstLoad) {
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
            addTodoItemToServer(todoItem)

            val newTodo = createTodo(todoItem)

            todoItemDao.insertNewTodoItemData(newTodo.toTodoDbEntity())
            todoItems.add(todoItem)
            updateFlow()
        }

    override suspend fun updateTodoItem(todoItem: TodoItem) =
        withContext(Dispatchers.IO) {
            val index = todoItems.indexOfFirst { it.id == todoItem.id }
            if (index != -1) {
                updateTodoItemOnServer(todoItem)

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
                removeTodoItemFromServer(todoItems[index].id)

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
                val dataFromServer = response.body() as TodoListResponse
                val todoItemsFromServer =
                    dataFromServer.list?.map { it.toTodoItem() }?.toMutableList() ?: listOf()

                val revisionFromServer = dataFromServer.revision
                if (revisionFromServer > revisionDao.getCurrentRevision()) {
                    dataWasUpdated = true
                    revisionDao.updateRevision(revisionFromServer)
                    todoItems.clear()
                    todoItems.addAll(todoItemsFromServer)
                    updateFlow()
                    updateDatabase()
                }
            } else {
                errorLoadLiveData.postValue(true)
            }

            return@withContext dataWasUpdated
        }

    private suspend fun addTodoItemToServer(todoItem: TodoItem) {
        val todoItemServer = toTodoItemServer(todoItem)
        val response = Common.apiService.addTodoItem(revisionDao.getCurrentRevision().toString(), ItemContainer(todoItemServer))

        updateRevision(response)
    }

    private suspend fun removeTodoItemFromServer(id: String) {
        val response = Common.apiService.deleteTodoItem(revisionDao.getCurrentRevision().toString(), id)
        updateRevision(response)
    }

    private suspend fun updateTodoItemOnServer(todoItem: TodoItem) {
        val todoItemServer = toTodoItemServer(todoItem)
        val response = Common.apiService.updateTodoItem(
            revisionDao.getCurrentRevision().toString(),
            todoItem.id,
            ItemContainer(todoItemServer)
        )

        updateRevision(response)
    }

    private fun updateRevision(response: Response<ItemResponse>) {
        if (response.isSuccessful) {
            val dataFromServer = response.body() as ItemResponse

            revisionDao.updateRevision(dataFromServer.revision)
        }
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