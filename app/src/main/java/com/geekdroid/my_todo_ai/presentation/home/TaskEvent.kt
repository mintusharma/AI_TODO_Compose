package com.geekdroid.my_todo_ai.presentation.home

import com.geekdroid.my_todo_ai.domain.model.Task

sealed class TaskEvent {
    data class AddTask(val task: Task) : TaskEvent()
    data class EditTask(val task: Task) : TaskEvent()
    data class DeleteTask(val task: Task) : TaskEvent()
    data class ToggleTaskCompletion(val task: Task) : TaskEvent()
    data object ToggleViewMode : TaskEvent()
}
