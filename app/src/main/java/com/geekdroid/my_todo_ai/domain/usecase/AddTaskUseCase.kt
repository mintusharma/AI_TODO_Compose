package com.geekdroid.my_todo_ai.domain.usecase

import com.geekdroid.my_todo_ai.domain.model.Task
import com.geekdroid.my_todo_ai.domain.repository.ReminderScheduler
import com.geekdroid.my_todo_ai.domain.repository.TaskRepository
import javax.inject.Inject

class AddTaskUseCase @Inject constructor(
    private val repository: TaskRepository,
    private val reminderScheduler: ReminderScheduler
) {
    suspend operator fun invoke(task: Task) {
        if (task.title.isBlank()) return
        val id = repository.insertTask(task)
        if (task.reminderTime != null) {
            reminderScheduler.schedule(task.copy(id = id.toInt()))
        }
    }
}
