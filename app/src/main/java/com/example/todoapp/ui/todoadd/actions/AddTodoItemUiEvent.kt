package com.example.todoapp.ui.todoadd.actions

sealed class AddTodoItemUiEvent {
    object NavigateUp: AddTodoItemUiEvent()
}
