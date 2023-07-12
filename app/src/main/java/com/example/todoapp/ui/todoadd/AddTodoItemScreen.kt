package com.example.todoapp.ui.todoadd

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.example.todoapp.data.item.Importance
import com.example.todoapp.ui.theme.ExtendedTheme
import com.example.todoapp.ui.theme.ThemeModePreview
import com.example.todoapp.ui.theme.TodoAppTheme
import com.example.todoapp.ui.todoadd.model.AddTodoItemState
import com.example.todoapp.ui.todoadd.actions.AddTodoItemUiAction
import com.example.todoapp.ui.todoadd.actions.AddTodoItemUiEvent
import com.example.todoapp.ui.todoadd.components.AddTodoItemDeadline
import com.example.todoapp.ui.todoadd.components.AddTodoItemDelete
import com.example.todoapp.ui.todoadd.components.AddTodoItemDivider
import com.example.todoapp.ui.todoadd.components.AddTodoItemImportance
import com.example.todoapp.ui.todoadd.components.AddTodoItemTextField
import com.example.todoapp.ui.todoadd.components.AddTodoItemTopAppBar
import com.example.todoapp.ui.todoadd.components.AddTodoItemUiActionHandler
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import java.util.Date

@Composable
fun AddTodoItemScreen(
    uiState: AddTodoItemState,
    uiEvent: Flow<AddTodoItemUiEvent>,
    uiAction: (AddTodoItemUiAction) -> Unit,
    onNavigateUp: () -> Unit,
    onSave: () -> Unit
) {
    AddTodoItemUiActionHandler(
        uiEvent = uiEvent,
        onNavigateUp = onNavigateUp,
        onSave = onSave
    )

    Scaffold(
        topBar = {
            AddTodoItemTopAppBar(text = uiState.text, uiAction = uiAction)
        },
        containerColor = ExtendedTheme.colors.backPrimary
    ) {paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            item {
                AddTodoItemTextField(
                    text = uiState.text,
                    uiAction = uiAction
                )
                AddTodoItemImportance(importance = uiState.importance, uiAction = uiAction)
                AddTodoItemDivider(padding = PaddingValues(horizontal = 16.dp))
                AddTodoItemDeadline(
                    deadline = uiState.deadline,
                    isDateVisible = uiState.isDeadlineSet,
                    uiAction = uiAction
                )
                AddTodoItemDivider(padding = PaddingValues(top = 16.dp, bottom = 8.dp))
                AddTodoItemDelete(enabled = uiState.isDeleteEnabled, uiAction = uiAction)
            }
        }
    }
}

@Preview
@Composable
fun PreviewAddTodoItemScreen(
    @PreviewParameter(ThemeModePreview::class) darkTheme: Boolean
) {
    val state = AddTodoItemState(
        text = "Text",
        importance = Importance.IMPORTANT,
        deadline = Date(),
        isDeadlineSet = true,
        isNewItem = false
    )
    val uiEvent = Channel<AddTodoItemUiEvent>().receiveAsFlow()

    TodoAppTheme(darkTheme = darkTheme) {
        AddTodoItemScreen(
            uiState = state,
            uiEvent = uiEvent,
            uiAction = {},
            onNavigateUp = {},
            onSave = {}
        )
    }
}
