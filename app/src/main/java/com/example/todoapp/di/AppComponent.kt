package com.example.todoapp.di

import android.content.Context
import com.example.todoapp.TodoApp
import com.example.todoapp.data.api.workmanager.DataUpdatesWorker
import com.example.todoapp.data.api.workmanager.NetworkAvailableWorker
import com.example.todoapp.data.api.workmanager.NetworkUnavailableWorker
import com.example.todoapp.di.module.ApiServiceModule
import com.example.todoapp.di.module.DataModule
import com.example.todoapp.di.module.WorkerModule
import com.example.todoapp.di.scope.AppScope
import dagger.BindsInstance
import dagger.Component

@AppScope
@Component(modules = [
    DataModule::class,
    WorkerModule::class,
    ApiServiceModule::class
])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun todoListFragmentComponent(): TodoListComponent
    fun addTodoItemFragmentComponent(): AddTodoItemComponent

    fun inject(todoApp: TodoApp)
    fun inject(worker: DataUpdatesWorker)
    fun inject(worker: NetworkAvailableWorker)
    fun inject(worker: NetworkUnavailableWorker)
}