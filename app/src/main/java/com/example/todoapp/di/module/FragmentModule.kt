package com.example.todoapp.di.module

import com.example.todoapp.data.Repository
import com.example.todoapp.di.scope.FragmentScope
import com.example.todoapp.ui.ViewModelFactory
import com.example.todoapp.ui.todolist.TodoListViewModel
import com.example.todoapp.ui.todolist.recyclerview.TodoItemsAdapter
import dagger.Module
import dagger.Provides

@Module
interface FragmentModule {
    companion object {
        @FragmentScope
        @Provides
        fun provideFactory(repository: Repository): ViewModelFactory {
            return ViewModelFactory(repository)
        }

        @FragmentScope
        @Provides
        fun provideTodoItemsAdapter(viewModel: TodoListViewModel): TodoItemsAdapter {
            return TodoItemsAdapter(viewModel::onUiAction)
        }
    }
}
