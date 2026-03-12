package com.geekdroid.my_todo_ai.di

import com.geekdroid.my_todo_ai.data.repository.PreferenceRepositoryImpl
import com.geekdroid.my_todo_ai.data.repository.TaskRepositoryImpl
import com.geekdroid.my_todo_ai.domain.repository.PreferenceRepository
import com.geekdroid.my_todo_ai.domain.repository.TaskRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindTaskRepository(
        taskRepositoryImpl: TaskRepositoryImpl
    ): TaskRepository

    @Binds
    @Singleton
    abstract fun bindPreferenceRepository(
        preferenceRepositoryImpl: PreferenceRepositoryImpl
    ): PreferenceRepository
}
