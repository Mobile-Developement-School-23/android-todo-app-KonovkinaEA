package com.example.todoapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todoapp.data.TodoItemsRepository
import com.example.todoapp.di.scope.FragmentScope
import com.example.todoapp.ui.todoadd.AddTodoItemViewModel
import com.example.todoapp.ui.todolist.TodoListViewModel

@FragmentScope
class ViewModelFactory(
    private val repository: TodoItemsRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(TodoListViewModel::class.java) -> {
                TodoListViewModel(repository) as T
            }
            modelClass.isAssignableFrom(AddTodoItemViewModel::class.java) -> {
                AddTodoItemViewModel(repository) as T
            }
            else -> {
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }
}