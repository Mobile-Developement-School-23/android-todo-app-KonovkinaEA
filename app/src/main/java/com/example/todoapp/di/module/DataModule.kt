package com.example.todoapp.di.module

import android.content.Context
import android.content.SharedPreferences
import com.example.todoapp.data.Repository
import com.example.todoapp.data.TodoItemsRepository
import com.example.todoapp.data.db.AppDatabase
import com.example.todoapp.data.db.RevisionDao
import com.example.todoapp.data.db.TodoItemDao
import com.example.todoapp.di.scope.AppScope
import com.example.todoapp.di.scope.ApplicationScope
import com.example.todoapp.ui.settings.model.ThemeMode
import com.example.todoapp.utils.THEME_KEY
import com.example.todoapp.utils.THEME_SYSTEM_ID
import com.example.todoapp.utils.getThemeById
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
        fun provideDatabase(context: Context): AppDatabase {
            return AppDatabase.getDatabaseInstance(context)
        }

        @AppScope
        @Provides
        fun provideSharedPreferences(context: Context): SharedPreferences {
            return context.getSharedPreferences("TABLE", Context.MODE_PRIVATE)
        }

        @AppScope
        @Provides
        fun provideThemeMode(pref: SharedPreferences): ThemeMode {
            val themeId = pref.getInt(THEME_KEY, THEME_SYSTEM_ID)
            return getThemeById(themeId)
        }
    }
}
