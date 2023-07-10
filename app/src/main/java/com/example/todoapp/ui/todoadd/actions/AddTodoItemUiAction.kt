package com.example.todoapp.ui.todoadd.actions

import com.example.todoapp.data.item.Importance

sealed class AddTodoItemUiAction {
    data class DescriptionChange(val text: String) : AddTodoItemUiAction()
    data class UpdateDeadlineVisibility(val visible: Boolean): AddTodoItemUiAction()
    data class UpdatePriority(val importance: Importance) : AddTodoItemUiAction()
    data class UpdateDeadline(val deadline: Long) : AddTodoItemUiAction()

    object SaveTask: AddTodoItemUiAction()
    object NavigateUp: AddTodoItemUiAction()
    object DeleteTask : AddTodoItemUiAction()
}