package com.example.todoapp.di.module

import android.app.AlarmManager
import android.content.Context
import com.example.todoapp.di.scope.FragmentContextQualifier
import com.example.todoapp.di.scope.FragmentScope
import dagger.Module
import dagger.Provides

@Module
interface NotificationModule {
    companion object {
        @FragmentScope
        @Provides
        fun provideAlarmManager(@FragmentContextQualifier context: Context): AlarmManager {
            return context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        }
    }
}