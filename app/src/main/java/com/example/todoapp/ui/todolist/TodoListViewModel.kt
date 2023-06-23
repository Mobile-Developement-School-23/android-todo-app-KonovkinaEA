package com.example.todoapp.ui.todolist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.item.TodoItem
import com.example.todoapp.data.local.HardCodedRepository
import com.example.todoapp.ui.todolist.actions.TodoListUiAction
import com.example.todoapp.ui.todolist.actions.TodoListUiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TodoListViewModel : ViewModel() {
    private val hardCodedRepository = HardCodedRepository.getInstance()

    private val _uiEvent = Channel<TodoListUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onUiAction(action: TodoListUiAction) {
        when (action) {
            is TodoListUiAction.EditTodoItem -> editTodoItem(action.todoItem)
            is TodoListUiAction.UpdateTodoItem -> updateTodoItem(action.todoItem)
            is TodoListUiAction.RemoveTodoItem -> removeTodoItemAt(action.index)
        }
    }

    fun getTodoItems() = hardCodedRepository.todoItems()

    private fun editTodoItem(todoItem: TodoItem) {
        viewModelScope.launch {
            _uiEvent.send(TodoListUiEvent.NavigateToEditTodoItem(todoItem.id))
        }
    }

    private fun updateTodoItem(todoItem: TodoItem) {
        viewModelScope.launch {
            hardCodedRepository.updateTodoItem(todoItem)
        }
    }

    private fun removeTodoItemAt(index: Int) {
        viewModelScope.launch {
            hardCodedRepository.removeTodoItemAt(index)
        }
    }
}