package com.geekdroid.my_todo_ai.presentation.home

import com.geekdroid.my_todo_ai.domain.model.Task

data class TaskUiState(
    val tasks: List<Task> = emptyList(),
    val isGridView: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
)
