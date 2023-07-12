package com.example.todoapp.di.module

import com.example.todoapp.data.TodoItemsRepository
import com.example.todoapp.di.scope.FragmentScope
import com.example.todoapp.ui.settings.SettingsViewModel
import com.example.todoapp.ui.settings.model.ThemeMode
import com.example.todoapp.ui.todoadd.AddTodoItemViewModel
import com.example.todoapp.ui.todolist.TodoListViewModel
import com.example.todoapp.ui.todolist.recyclerview.PreviewOffsetTodoItemDecoration
import com.example.todoapp.ui.todolist.recyclerview.TodoItemsAdapter
import com.example.todoapp.utils.toPx
import dagger.Module
import dagger.Provides

@Module
interface FragmentModule {
    companion object {
        @FragmentScope
        @Provides
        fun provideSettingsViewModel(themeMode: ThemeMode): SettingsViewModel {
            return SettingsViewModel(themeMode)
        }
        @FragmentScope
        @Provides
        fun provideTodoListViewModel(repository: TodoItemsRepository): TodoListViewModel {
            return TodoListViewModel(repository)
        }

        @FragmentScope
        @Provides
        fun provideAddTodoItemViewModel(repository: TodoItemsRepository): AddTodoItemViewModel {
            return AddTodoItemViewModel(repository)
        }
        @FragmentScope
        @Provides
        fun providePreviewOffsetDecoration(): PreviewOffsetTodoItemDecoration {
            return PreviewOffsetTodoItemDecoration(bottomOffset = 16f.toPx.toInt())
        }

        @FragmentScope
        @Provides
        fun provideTodoItemsAdapter(viewModel: TodoListViewModel): TodoItemsAdapter {
            return TodoItemsAdapter(viewModel::onUiAction)
        }

        @FragmentScope
        @Provides
        fun provideThemeMode(): ThemeMode {
            return ThemeMode.SYSTEM
        }
    }
}
