package com.geekdroid.my_todo_ai.presentation.task_detail

import com.geekdroid.my_todo_ai.domain.model.Priority
import java.time.LocalDateTime

sealed class AddEditTaskEvent {
    data class EnteredTitle(val title: String) : AddEditTaskEvent()
    data class EnteredDescription(val description: String) : AddEditTaskEvent()
    data class EnteredCategory(val category: String) : AddEditTaskEvent()
    data class PriorityChanged(val priority: Priority) : AddEditTaskEvent()
    data class ReminderTimeChanged(val reminderTime: LocalDateTime?) : AddEditTaskEvent()
    data object SaveTask : AddEditTaskEvent()
}
