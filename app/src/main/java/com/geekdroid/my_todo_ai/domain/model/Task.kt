package com.geekdroid.my_todo_ai.domain.model

import java.time.LocalDateTime

data class Task(
    val id: Int = 0,
    val title: String,
    val description: String,
    val category: String,
    val priority: Priority,
    val reminderTime: LocalDateTime?,
    val isCompleted: Boolean = false,
    val createdAt: LocalDateTime = LocalDateTime.now()
)
