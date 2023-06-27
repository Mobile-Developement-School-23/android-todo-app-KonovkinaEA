package com.example.todoapp.ui.todolist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.Dependencies
import com.example.todoapp.data.item.TodoItem
import com.example.todoapp.ui.todolist.actions.TodoListUiAction
import com.example.todoapp.ui.todolist.actions.TodoListUiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class TodoListViewModel : ViewModel() {
    private val todoItemsRepository = Dependencies.todoItemsDbRepository

    private val _uiEvent = Channel<TodoListUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onUiAction(action: TodoListUiAction) {
        when (action) {
            is TodoListUiAction.EditTodoItem -> editTodoItem(action.todoItem)
            is TodoListUiAction.UpdateTodoItem -> updateTodoItem(action.todoItem)
            is TodoListUiAction.RemoveTodoItem -> removeTodoItem(action.todoItem)
        }
    }

    suspend fun getTodoItems() = todoItemsRepository.todoItems()

    private fun editTodoItem(todoItem: TodoItem) {
        viewModelScope.launch {
            _uiEvent.send(TodoListUiEvent.NavigateToEditTodoItem(todoItem.id))
        }
    }

    private fun updateTodoItem(todoItem: TodoItem) {
        viewModelScope.launch {
            todoItemsRepository.updateTodoItem(todoItem)
        }
    }

    private fun removeTodoItem(todoItem: TodoItem) {
        viewModelScope.launch {
            todoItemsRepository.removeTodoItem(todoItem.id)
        }
    }
}