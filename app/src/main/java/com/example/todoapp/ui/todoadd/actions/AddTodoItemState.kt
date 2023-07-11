package com.example.todoapp.ui.todoadd.actions

import com.example.todoapp.data.item.Importance
import java.util.Date

data class AddTodoItemState(
    val text: String = "",
    val importance: Importance = Importance.LOW,
    val deadline: Date = Date(),
    val isDeadlineSet: Boolean = false,
    val isNewItem: Boolean = true
) {
    val isDeleteEnabled: Boolean
        get() = text.isNotBlank() || !isNewItem
}
