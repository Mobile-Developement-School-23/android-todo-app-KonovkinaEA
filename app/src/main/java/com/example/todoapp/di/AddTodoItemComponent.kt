package com.example.todoapp.di

import com.example.todoapp.di.module.FragmentModule
import com.example.todoapp.di.scope.FragmentScope
import com.example.todoapp.ui.todoadd.AddTodoItemFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [FragmentModule::class])
interface AddTodoItemComponent {
    fun inject(fragment: AddTodoItemFragment)
}