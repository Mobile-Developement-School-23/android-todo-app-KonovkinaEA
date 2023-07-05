package com.example.todoapp.di

import com.example.todoapp.di.module.FragmentModule
import com.example.todoapp.di.scope.FragmentScope
import com.example.todoapp.ui.todolist.TodoListFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [FragmentModule::class])
interface TodoListComponent {
    fun inject(fragment: TodoListFragment)
}