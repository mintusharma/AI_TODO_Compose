package com.geekdroid.my_todo_ai.domain.repository

import com.geekdroid.my_todo_ai.domain.model.Task

interface ReminderScheduler {
    fun schedule(task: Task)
    fun cancel(task: Task)
}
