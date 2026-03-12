package com.geekdroid.my_todo_ai.domain.usecase

import com.geekdroid.my_todo_ai.domain.model.Task
import com.geekdroid.my_todo_ai.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTasksUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    operator fun invoke(): Flow<List<Task>> {
        return repository.getAllTasks()
    }
}
