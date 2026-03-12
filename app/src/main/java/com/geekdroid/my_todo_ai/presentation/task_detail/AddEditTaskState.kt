package com.geekdroid.my_todo_ai.presentation.task_detail

import com.geekdroid.my_todo_ai.domain.model.Priority
import java.time.LocalDateTime

data class AddEditTaskState(
    val title: String = "",
    val description: String = "",
    val category: String = "",
    val priority: Priority = Priority.MEDIUM,
    val reminderTime: LocalDateTime? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isTaskSaved: Boolean = false
)
