package com.geekdroid.my_todo_ai.domain.usecase

import com.geekdroid.my_todo_ai.domain.model.Task
import com.geekdroid.my_todo_ai.domain.repository.ReminderScheduler
import com.geekdroid.my_todo_ai.domain.repository.TaskRepository
import javax.inject.Inject

class EditTaskUseCase @Inject constructor(
    private val repository: TaskRepository,
    private val reminderScheduler: ReminderScheduler
) {
    suspend operator fun invoke(task: Task) {
        repository.updateTask(task)
        if (task.reminderTime != null && !task.isCompleted) {
            reminderScheduler.schedule(task)
        } else {
            reminderScheduler.cancel(task)
        }
    }
}
