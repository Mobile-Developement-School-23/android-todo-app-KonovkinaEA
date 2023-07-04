package com.example.todoapp.di

import android.content.Context
import com.example.todoapp.TodoApp
import com.example.todoapp.data.api.workmanager.DataUpdatesWorker
import com.example.todoapp.data.api.workmanager.NetworkAvailableWorker
import com.example.todoapp.data.api.workmanager.NetworkUnavailableWorker
import com.example.todoapp.di.module.DataModule
import com.example.todoapp.di.scope.AppScope
import com.example.todoapp.ui.todoadd.AddTodoItemFragment
import com.example.todoapp.ui.todolist.TodoListFragment
import dagger.BindsInstance
import dagger.Component

@AppScope
@Component(modules = [DataModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun fragmentComponent(): FragmentComponent

    fun inject(todoApp: TodoApp)
//    fun inject(fragment: TodoListFragment)
//    fun inject(fragment: AddTodoItemFragment)
    fun inject(worker: DataUpdatesWorker)
    fun inject(worker: NetworkAvailableWorker)
    fun inject(worker: NetworkUnavailableWorker)
}