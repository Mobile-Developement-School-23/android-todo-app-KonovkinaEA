package com.example.todoapp.di.module

import androidx.lifecycle.ViewModelProvider
import com.example.todoapp.di.scope.FragmentScope
import com.example.todoapp.ui.ViewModelFactory
import com.example.todoapp.ui.todoadd.AddTodoItemFragment
import com.example.todoapp.ui.todoadd.AddTodoItemViewModel
import com.example.todoapp.ui.todolist.TodoListFragment
import com.example.todoapp.ui.todolist.TodoListViewModel
import dagger.Module
import dagger.Provides

@Module
interface ViewModelModule {
    companion object {
//        @FragmentScope
//        @Provides
//        fun provideTodoListViewModel(
//            fragment: TodoListFragment,
//            viewModelFactory: ViewModelFactory
//        ): TodoListViewModel {
//            return ViewModelProvider(
//                fragment,
//                viewModelFactory
//            )[TodoListViewModel::class.java]
//        }
//
//        @FragmentScope
//        @Provides
//        fun provideAddTodoItemViewModel(
//            fragment: AddTodoItemFragment,
//            viewModelFactory: ViewModelFactory
//        ): AddTodoItemViewModel {
//            return ViewModelProvider(
//                fragment,
//                viewModelFactory
//            )[AddTodoItemViewModel::class.java]
//        }
    }
}