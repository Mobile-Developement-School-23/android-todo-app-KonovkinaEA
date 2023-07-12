package com.example.todoapp.ui.todoadd

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.TodoItemsRepository
import com.example.todoapp.data.item.TodoItem
import com.example.todoapp.di.scope.FragmentScope
import com.example.todoapp.ui.todoadd.model.AddTodoItemState
import com.example.todoapp.ui.todoadd.actions.AddTodoItemUiAction
import com.example.todoapp.ui.todoadd.actions.AddTodoItemUiEvent
import com.example.todoapp.utils.dateToUnix
import com.example.todoapp.utils.unixToDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date

@FragmentScope
class AddTodoItemViewModel(
    private val repository: TodoItemsRepository
) : ViewModel() {
    private var oldTodoItem: TodoItem? = null
    private lateinit var id: String
    private var isNewItem: Boolean = true

    private val _uiEvent = Channel<AddTodoItemUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _uiState = MutableStateFlow(AddTodoItemState())
    val uiState = _uiState.asStateFlow()

    fun init(args: AddTodoItemFragmentArgs) {
        viewModelScope.launch {
            id = args.id
            repository.getTodoItem(id)?.let { todoItem ->
                oldTodoItem = todoItem
                isNewItem = false

                _uiState.update { uiState.value.copy(
                    text = todoItem.text,
                    importance = todoItem.importance,
                    deadline = if (todoItem.deadline != null)
                        unixToDate(todoItem.deadline!!) else uiState.value.deadline,
                    isDeadlineSet = todoItem.deadline != null,
                    isNewItem = false
                ) }
            }
        }
    }

    fun onUiAction(action: AddTodoItemUiAction) {
        when(action) {
            AddTodoItemUiAction.SaveTask -> saveTodoItem()
            AddTodoItemUiAction.DeleteTask -> removeTodoItem()
            AddTodoItemUiAction.NavigateUp -> viewModelScope.launch {
                _uiEvent.send(AddTodoItemUiEvent.NavigateUp)
            }
            is AddTodoItemUiAction.UpdateText -> _uiState.update {
                uiState.value.copy(text = action.text)
            }
            is AddTodoItemUiAction.UpdateDeadlineSet -> _uiState.update {
                uiState.value.copy(isDeadlineSet = action.isDeadlineSet)
            }
            is AddTodoItemUiAction.UpdateImportance -> _uiState.update {
                uiState.value.copy(importance = action.importance)
            }
            is AddTodoItemUiAction.UpdateDeadline -> _uiState.update {
                uiState.value.copy(deadline = unixToDate(action.deadline))
            }
        }
    }

    private fun saveTodoItem() {
        if (uiState.value.text.isBlank()) return

        val todoItem = if (isNewItem) {
            TodoItem(
                id = id,
                text = uiState.value.text,
                importance = uiState.value.importance,
                deadline = if (uiState.value.isDeadlineSet) dateToUnix(uiState.value.deadline) else null
            )
        } else {
            oldTodoItem!!.copy(
                text = uiState.value.text,
                importance = uiState.value.importance,
                deadline = if (uiState.value.isDeadlineSet) dateToUnix(uiState.value.deadline) else null,
                modificationDate = dateToUnix(Date())
            )
        }

        viewModelScope.launch(Dispatchers.IO) {
            if (isNewItem) repository.addTodoItem(todoItem)
            else repository.updateTodoItem(todoItem)
            _uiEvent.send(AddTodoItemUiEvent.SaveTodoItem)
        }
    }

    private fun removeTodoItem() {
        viewModelScope.launch(Dispatchers.IO) {
            if (!isNewItem)
                oldTodoItem?.let { repository.removeTodoItem(id) }
            _uiEvent.send(AddTodoItemUiEvent.NavigateUp)
        }
    }
}
