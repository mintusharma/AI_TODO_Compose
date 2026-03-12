package com.geekdroid.my_todo_ai.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geekdroid.my_todo_ai.domain.model.Task
import com.geekdroid.my_todo_ai.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val getTasksUseCase: GetTasksUseCase,
    private val addTaskUseCase: AddTaskUseCase,
    private val editTaskUseCase: EditTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val toggleViewModeUseCase: ToggleViewModeUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(TaskUiState())
    val state: StateFlow<TaskUiState> = _state.asStateFlow()

    init {
        observeTasks()
        observeViewMode()
    }

    private fun observeTasks() {
        getTasksUseCase()
            .onEach { tasks ->
                _state.update { it.copy(tasks = tasks) }
            }
            .catch { e ->
                _state.update { it.copy(error = e.message) }
            }
            .launchIn(viewModelScope)
    }

    private fun observeViewMode() {
        toggleViewModeUseCase.isGridView
            .onEach { isGridView ->
                _state.update { it.copy(isGridView = isGridView) }
            }
            .launchIn(viewModelScope)
    }

    fun onEvent(event: TaskEvent) {
        when (event) {
            is TaskEvent.AddTask -> {
                viewModelScope.launch {
                    addTaskUseCase(event.task)
                }
            }
            is TaskEvent.EditTask -> {
                viewModelScope.launch {
                    editTaskUseCase(event.task)
                }
            }
            is TaskEvent.DeleteTask -> {
                viewModelScope.launch {
                    deleteTaskUseCase(event.task)
                }
            }
            TaskEvent.ToggleViewMode -> {
                viewModelScope.launch {
                    toggleViewModeUseCase()
                }
            }
            is TaskEvent.ToggleTaskCompletion -> {
                viewModelScope.launch {
                    val updatedTask = event.task.copy(isCompleted = !event.task.isCompleted)
                    editTaskUseCase(updatedTask)
                }
            }
        }
    }
}
