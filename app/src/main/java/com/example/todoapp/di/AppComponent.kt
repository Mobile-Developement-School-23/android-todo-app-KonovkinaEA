package com.example.todoapp.di

import android.app.Application
import android.content.Context
import com.example.todoapp.TodoApp
import com.example.todoapp.di.module.DataModule
import com.example.todoapp.di.scope.AppScope
import dagger.BindsInstance
import dagger.Component

@AppScope
@Component(modules = [DataModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(todoApp: TodoApp)
}