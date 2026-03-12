package com.geekdroid.my_todo_ai.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.geekdroid.my_todo_ai.domain.model.Priority
import com.geekdroid.my_todo_ai.domain.model.Task
import java.time.LocalDateTime

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val category: String,
    val priority: Priority,
    val reminderTime: LocalDateTime?,
    val isCompleted: Boolean,
    val createdAt: LocalDateTime
)

fun TaskEntity.toDomain(): Task {
    return Task(
        id = id,
        title = title,
        description = description,
        category = category,
        priority = priority,
        reminderTime = reminderTime,
        isCompleted = isCompleted,
        createdAt = createdAt
    )
}

fun Task.toEntity(): TaskEntity {
    return TaskEntity(
        id = id,
        title = title,
        description = description,
        category = category,
        priority = priority,
        reminderTime = reminderTime,
        isCompleted = isCompleted,
        createdAt = createdAt
    )
}
