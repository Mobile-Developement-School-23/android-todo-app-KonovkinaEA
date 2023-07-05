package com.example.todoapp.data

import androidx.lifecycle.MutableLiveData
import com.example.todoapp.data.api.ApiService
import com.example.todoapp.data.api.model.ItemContainer
import com.example.todoapp.data.api.model.ItemResponse
import com.example.todoapp.data.api.model.TodoListContainer
import com.example.todoapp.data.api.model.TodoListResponse
import com.example.todoapp.data.api.workmanager.CustomWorkManager
import com.example.todoapp.data.db.RevisionDao
import com.example.todoapp.data.item.TodoItem
import com.example.todoapp.data.db.TodoItemDao
import com.example.todoapp.di.scope.AppScope
import com.example.todoapp.utils.createTodo
import com.example.todoapp.utils.toTodoItemServer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

@AppScope
class Repository @Inject constructor(
    private val todoItemDao: TodoItemDao,
    private val revisionDao: RevisionDao,
    private val workManager: CustomWorkManager,
    private val apiService: ApiService
) : TodoItemsRepository {
    private val todoItems: MutableList<TodoItem> = mutableListOf()
    private val todoItemsFlow: MutableStateFlow<List<TodoItem>> = MutableStateFlow(mutableListOf())

    private val errorListLiveData = MutableLiveData<Boolean>()
    private val errorItemLiveData = MutableLiveData<Boolean>()

    override suspend fun loadDataFromServer() =
        withContext(Dispatchers.IO) {
            val response = apiService.getAllTodoData()

            if (response.isSuccessful) {
                val dataFromServer = response.body() as TodoListResponse
                if (dataFromServer.revision > revisionDao.getCurrentRevision()) {
                    updateDataDB(dataFromServer)
                } else {
                    updateDataOnServer()
                }
                errorListLiveData.postValue(false)
            } else {
                loadDataFromDB()
                errorListLiveData.postValue(true)
            }
        }

    override suspend fun loadDataFromDB() =
        withContext(Dispatchers.IO) {
            updateTodoItems(todoItemDao.getAllTodoData().map { it.toTodoItem() })
            errorListLiveData.postValue(true)
        }

    override fun reloadData() {
        workManager.reloadData()
    }

    override fun errorListLiveData() = errorListLiveData

    override fun errorItemLiveData() = errorItemLiveData

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

    private suspend fun updateDataOnServer() =
        withContext(Dispatchers.IO) {
            updateTodoItems(todoItemDao.getAllTodoData().map { it.toTodoItem() })

            val todoListServer = TodoListContainer(todoItems.map { toTodoItemServer(it) })
            val response = apiService.patchList(revisionDao.getCurrentRevision().toString(), todoListServer)

            if (response.isSuccessful) {
                val dataFromServer = response.body() as TodoListResponse
                revisionDao.updateRevision(dataFromServer.revision)
                errorItemLiveData.postValue(false)
            } else {
                errorListLiveData.postValue(true)
            }
        }

    private suspend fun updateDataDB(dataFromServer: TodoListResponse) =
        withContext(Dispatchers.IO) {
            val todoItemsFromServer =
                dataFromServer.list?.map { it.toTodoItem() }?.toMutableList() ?: listOf()

            revisionDao.updateRevision(dataFromServer.revision)
            updateTodoItems(todoItemsFromServer)
            updateDatabase()
        }

    private suspend fun addTodoItemToServer(todoItem: TodoItem) =
        withContext(Dispatchers.IO) {
            if (workManager.isNetworkAvailable()) {
                val todoItemServer = toTodoItemServer(todoItem)
                val response = apiService.addTodoItem(revisionDao.getCurrentRevision().toString(), ItemContainer(todoItemServer))

                updateRevision(response)
            }
        }

    private suspend fun removeTodoItemFromServer(id: String) =
        withContext(Dispatchers.IO) {
            if (workManager.isNetworkAvailable()) {
                val response = apiService.deleteTodoItem(revisionDao.getCurrentRevision().toString(), id)
                updateRevision(response)
            }
        }

    private suspend fun updateTodoItemOnServer(todoItem: TodoItem) =
        withContext(Dispatchers.IO) {
            if (workManager.isNetworkAvailable()) {
                val todoItemServer = toTodoItemServer(todoItem)
                val response = apiService.updateTodoItem(
                    revisionDao.getCurrentRevision().toString(),
                    todoItem.id,
                    ItemContainer(todoItemServer)
                )

                updateRevision(response)
            }
        }

    private suspend fun updateRevision(response: Response<ItemResponse>) =
        withContext(Dispatchers.IO) {
            if (response.isSuccessful) {
                val dataFromServer = response.body() as ItemResponse
                revisionDao.updateRevision(dataFromServer.revision)
            } else {
                errorItemLiveData.postValue(true)
            }
        }

    private suspend fun updateDatabase() =
        withContext(Dispatchers.IO) {
            val todoDbList = todoItems.map { createTodo(it).toTodoDbEntity() }
            todoItemDao.replaceAllTodoItems(todoDbList)
        }

    private fun updateTodoItems(newList: List<TodoItem>) {
        todoItems.clear()
        todoItems.addAll(newList)
        updateFlow()
    }

    private fun updateFlow() {
        todoItemsFlow.update {
            todoItems.toList()
        }
    }
}