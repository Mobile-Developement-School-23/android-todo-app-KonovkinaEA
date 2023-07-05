package com.example.todoapp.di

import com.example.todoapp.di.module.ViewModelModule
import com.example.todoapp.di.scope.FragmentScope
import com.example.todoapp.ui.todolist.TodoListFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [ViewModelModule::class])
interface TodoListFragmentComponent {
    fun inject(fragment: TodoListFragment)
}