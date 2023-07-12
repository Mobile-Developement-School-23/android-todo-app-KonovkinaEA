package com.example.todoapp.ui.todoadd.actions

import com.example.todoapp.data.item.Importance

sealed class AddTodoItemUiAction {
    data class UpdateText(val text: String) : AddTodoItemUiAction()
    data class UpdateDeadlineSet(val isDeadlineSet: Boolean): AddTodoItemUiAction()
    data class UpdateDeadline(val deadline: Long) : AddTodoItemUiAction()
    data class UpdateImportance(val importance: Importance) : AddTodoItemUiAction()

    object SaveTask: AddTodoItemUiAction()
    object DeleteTask : AddTodoItemUiAction()
    object NavigateUp: AddTodoItemUiAction()
}
