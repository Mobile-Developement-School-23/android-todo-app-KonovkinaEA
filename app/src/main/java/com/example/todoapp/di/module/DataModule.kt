package com.example.todoapp.di.module

import com.example.todoapp.Dependencies
import com.example.todoapp.data.Repository
import com.example.todoapp.data.TodoItemsRepository
import com.example.todoapp.data.db.AppDatabase
import com.example.todoapp.data.db.RevisionDao
import com.example.todoapp.data.db.TodoItemDao
import com.example.todoapp.di.scope.AppScope
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @AppScope
    @Binds
    fun provideRepository(repository: Repository): TodoItemsRepository
    companion object {
        @AppScope
        @Provides
        fun provideTodoItemDao(database: AppDatabase): TodoItemDao {
            return database.getTodoItemDao()
        }

        @AppScope
        @Provides
        fun provideRevisionDao(database: AppDatabase): RevisionDao {
            return database.getRevisionDao()
        }

        @AppScope
        @Provides
        fun provideDatabase(): AppDatabase {
            return Dependencies.appDatabase
        }
    }
}