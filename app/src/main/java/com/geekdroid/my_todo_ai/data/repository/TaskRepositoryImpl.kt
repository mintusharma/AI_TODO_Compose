package com.geekdroid.my_todo_ai.data.repository

import com.geekdroid.my_todo_ai.data.local.dao.TaskDao
import com.geekdroid.my_todo_ai.data.local.entity.toDomain
import com.geekdroid.my_todo_ai.data.local.entity.toEntity
import com.geekdroid.my_todo_ai.domain.model.Task
import com.geekdroid.my_todo_ai.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val taskDao: TaskDao
) : TaskRepository {

    override fun getAllTasks(): Flow<List<Task>> {
        return taskDao.getAllTasks().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getTaskById(id: Int): Flow<Task?> {
        return taskDao.getTaskById(id).map { it?.toDomain() }
    }

    override suspend fun insertTask(task: Task): Long {
        return taskDao.insertTask(task.toEntity())
    }

    override suspend fun updateTask(task: Task) {
        taskDao.updateTask(task.toEntity())
    }

    override suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task.toEntity())
    }
}
