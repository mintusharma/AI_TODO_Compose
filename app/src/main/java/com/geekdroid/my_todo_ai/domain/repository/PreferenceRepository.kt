package com.geekdroid.my_todo_ai.domain.repository

import kotlinx.coroutines.flow.Flow

interface PreferenceRepository {
    val isGridView: Flow<Boolean>
    suspend fun toggleViewMode()
}
