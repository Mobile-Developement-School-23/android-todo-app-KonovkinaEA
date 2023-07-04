package com.example.todoapp.di.module

import android.content.Context
import android.net.ConnectivityManager
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
//        @AppScope
//        @Provides
//        fun provideWorkManager(): WorkManager {
//            return WorkManager()
//        }

        @AppScope
        @Provides
        fun provideWorkManagerInstance(context: Context): androidx.work.WorkManager {
            return androidx.work.WorkManager.getInstance(context)
        }

        @AppScope
        @Provides
        fun provideConnectivityManager(context: Context): ConnectivityManager {
            return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        }

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
        fun provideDatabase(context: Context): AppDatabase {
            return AppDatabase.getDatabaseInstance(context)
        }
    }
}