package com.geekdroid.my_todo_ai.domain.repository

import com.geekdroid.my_todo_ai.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getAllTasks(): Flow<List<Task>>
    fun getTaskById(id: Int): Flow<Task?>
    suspend fun insertTask(task: Task): Long
    suspend fun updateTask(task: Task)
    suspend fun deleteTask(task: Task)
}
