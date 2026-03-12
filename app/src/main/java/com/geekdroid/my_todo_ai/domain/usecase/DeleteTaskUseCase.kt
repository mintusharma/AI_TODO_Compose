package com.geekdroid.my_todo_ai.domain.usecase

import com.geekdroid.my_todo_ai.domain.model.Task
import com.geekdroid.my_todo_ai.domain.repository.ReminderScheduler
import com.geekdroid.my_todo_ai.domain.repository.TaskRepository
import javax.inject.Inject

class DeleteTaskUseCase @Inject constructor(
    private val repository: TaskRepository,
    private val reminderScheduler: ReminderScheduler
) {
    suspend operator fun invoke(task: Task) {
        repository.deleteTask(task)
        reminderScheduler.cancel(task)
    }
}
