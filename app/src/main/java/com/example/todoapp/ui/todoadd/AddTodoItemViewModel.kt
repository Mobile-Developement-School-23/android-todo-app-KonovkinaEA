package com.example.todoapp.ui.todoadd

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.TodoItemsRepository
import com.example.todoapp.data.item.Importance
import com.example.todoapp.data.item.TodoItem
import com.example.todoapp.di.scope.FragmentScope
import com.example.todoapp.ui.todoadd.actions.AddTodoItemState
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

    fun onAction(action: AddTodoItemUiAction) {
        when(action) {
            is AddTodoItemUiAction.DescriptionChange -> _uiState.update {
                uiState.value.copy(text = action.text)
            }
            is AddTodoItemUiAction.UpdateDeadlineVisibility -> _uiState.update {
                uiState.value.copy(isDeadlineSet = action.visible)
            }
            is AddTodoItemUiAction.UpdatePriority -> _uiState.update {
                uiState.value.copy(importance = action.importance)
            }
            is AddTodoItemUiAction.UpdateDeadline -> _uiState.update {
                uiState.value.copy(deadline = unixToDate(action.deadline))
            }
            AddTodoItemUiAction.SaveTask -> saveTodoItem()
            AddTodoItemUiAction.DeleteTask -> removeTodoItem()
            AddTodoItemUiAction.NavigateUp -> viewModelScope.launch {
                _uiEvent.send(AddTodoItemUiEvent.NavigateUp)
            }
        }
    }

    fun findTodoItem(args: AddTodoItemFragmentArgs) {
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
//                updateText(todoItem.text)
//                updateImportance(todoItem.importance)
//                todoItem.deadline?.let {
//                    updateDeadline(it)
//                    updateIsDeadlineSet(true)
//                }
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

//    private val _text = MutableStateFlow("")
//    val text = _text.asStateFlow()
//
//    private val _importance = MutableStateFlow(Importance.LOW)
//    val importance = _importance.asStateFlow()
//
//    private val _deadline = MutableStateFlow(dateToUnix(Date()))
//    val deadline = _deadline.asStateFlow()
//
//    private val _isDeadlineSet = MutableStateFlow(false)
//    val isDeadlineSet = _isDeadlineSet.asStateFlow()
//
//    fun findTodoItem(args: AddTodoItemFragmentArgs) {
//        viewModelScope.launch {
//            id = args.id
//            repository.getTodoItem(id)?.let { todoItem ->
//                oldTodoItem = todoItem
//                isNewItem = false
//                updateText(todoItem.text)
//                updateImportance(todoItem.importance)
//                todoItem.deadline?.let {
//                    updateDeadline(it)
//                    updateIsDeadlineSet(true)
//                }
//            }
//        }
//    }
//
//    fun updateText(text: String) {
//        _text.update { text }
//    }
//
//    fun updateImportance(importance: Importance) {
//        _importance.update { importance }
//    }
//
//    fun updateDeadline(deadline: Long) {
//        _deadline.update { deadline }
//    }
//
//    fun updateIsDeadlineSet(isDeadlineSet: Boolean) {
//        _isDeadlineSet.update { isDeadlineSet }
//    }
//
//    fun saveTodoItem() {
//        val todoItem = if (isNewItem) {
//            TodoItem(
//                id = id,
//                text = _text.value,
//                importance = _importance.value,
//                deadline = if (_isDeadlineSet.value) _deadline.value else null
//            )
//        } else {
//            oldTodoItem!!.copy(
//                text = _text.value,
//                importance = _importance.value,
//                deadline = if (_isDeadlineSet.value) _deadline.value else null,
//                modificationDate = dateToUnix(Date())
//            )
//        }
//
//        viewModelScope.launch {
//            if (isNewItem) repository.addTodoItem(todoItem)
//            else repository.updateTodoItem(todoItem)
//            _uiEvent.send(AddTodoItemUiEvent.NavigateUp)
//        }
//    }
//
//    fun removeTodoItem() {
//        viewModelScope.launch {
//            if (!isNewItem)
//                oldTodoItem?.let { repository.removeTodoItem(id) }
//            _uiEvent.send(AddTodoItemUiEvent.NavigateUp)
//        }
//    }
}
