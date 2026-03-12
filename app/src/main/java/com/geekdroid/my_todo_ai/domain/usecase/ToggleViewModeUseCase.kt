package com.geekdroid.my_todo_ai.domain.usecase

import com.geekdroid.my_todo_ai.domain.repository.PreferenceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ToggleViewModeUseCase @Inject constructor(
    private val repository: PreferenceRepository
) {
    val isGridView: Flow<Boolean> = repository.isGridView

    suspend operator fun invoke() {
        repository.toggleViewMode()
    }
}
