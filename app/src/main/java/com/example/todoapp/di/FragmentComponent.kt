package com.example.todoapp.di

import com.example.todoapp.di.scope.FragmentScope
import com.example.todoapp.ui.todoadd.AddTodoItemFragment
import com.example.todoapp.ui.todolist.TodoListFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent
interface FragmentComponent {
    fun inject(fragment: TodoListFragment)
    fun inject(fragment: AddTodoItemFragment)
}