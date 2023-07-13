package com.example.todoapp.di

import android.content.Context
import com.example.todoapp.di.module.FragmentModule
import com.example.todoapp.di.module.NotificationModule
import com.example.todoapp.di.scope.FragmentContextQualifier
import com.example.todoapp.di.scope.FragmentScope
import com.example.todoapp.ui.todoadd.AddTodoItemFragment
import dagger.BindsInstance
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [
    FragmentModule::class,
    NotificationModule::class
])
interface AddTodoItemComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance @FragmentContextQualifier context: Context): AddTodoItemComponent
    }

    fun inject(fragment: AddTodoItemFragment)
}
