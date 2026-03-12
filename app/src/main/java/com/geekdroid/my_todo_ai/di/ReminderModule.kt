package com.geekdroid.my_todo_ai.di

import com.geekdroid.my_todo_ai.data.local.ReminderSchedulerImpl
import com.geekdroid.my_todo_ai.domain.repository.ReminderScheduler
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ReminderModule {

    @Binds
    @Singleton
    abstract fun bindReminderScheduler(
        reminderSchedulerImpl: ReminderSchedulerImpl
    ): ReminderScheduler
}
