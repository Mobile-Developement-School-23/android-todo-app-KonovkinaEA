package com.example.todoapp.ui.todoadd.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.example.todoapp.ui.todoadd.actions.AddTodoItemUiAction
import com.example.todoapp.ui.todoadd.actions.AddTodoItemUiEvent
import kotlinx.coroutines.flow.Flow

@Composable
fun AddTodoItemUiActionHandler(
    uiEvent: Flow<AddTodoItemUiEvent>,
    onNavigateUp: () -> Unit,
    onSave: () -> Unit
) {
    LaunchedEffect(Unit) {
        uiEvent.collect {
            when(it) {
                AddTodoItemUiEvent.NavigateUp -> onNavigateUp()
                AddTodoItemUiEvent.SaveTodoItem -> onSave()
            }
        }
    }
}
