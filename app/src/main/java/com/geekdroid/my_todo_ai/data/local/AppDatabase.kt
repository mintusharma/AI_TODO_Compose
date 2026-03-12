package com.geekdroid.my_todo_ai.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.geekdroid.my_todo_ai.data.local.converter.DateConverter
import com.geekdroid.my_todo_ai.data.local.dao.TaskDao
import com.geekdroid.my_todo_ai.data.local.entity.TaskEntity

@Database(entities = [TaskEntity::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao

    companion object {
        const val DATABASE_NAME = "todo_db"
    }
}
