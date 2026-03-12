package com.geekdroid.my_todo_ai.presentation.task_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geekdroid.my_todo_ai.domain.model.Task
import com.geekdroid.my_todo_ai.domain.usecase.AddTaskUseCase
import com.geekdroid.my_todo_ai.domain.usecase.EditTaskUseCase
import com.geekdroid.my_todo_ai.domain.usecase.GetTaskByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditTaskViewModel @Inject constructor(
    private val getTaskByIdUseCase: GetTaskByIdUseCase,
    private val addTaskUseCase: AddTaskUseCase,
    private val editTaskUseCase: EditTaskUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(AddEditTaskState())
    val state: StateFlow<AddEditTaskState> = _state.asStateFlow()

    private var currentTaskId: Int? = null

    init {
        savedStateHandle.get<Int>("taskId")?.let { taskId ->
            if (taskId != -1) {
                loadTask(taskId)
            }
        }
    }

    private fun loadTask(taskId: Int) {
        getTaskByIdUseCase(taskId)
            .onEach { task ->
                task?.let {
                    currentTaskId = it.id
                    _state.update { s ->
                        s.copy(
                            title = it.title,
                            description = it.description,
                            category = it.category,
                            priority = it.priority,
                            reminderTime = it.reminderTime
                        )
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    fun onEvent(event: AddEditTaskEvent) {
        when (event) {
            is AddEditTaskEvent.EnteredTitle -> {
                _state.update { it.copy(title = event.title) }
            }
            is AddEditTaskEvent.EnteredDescription -> {
                _state.update { it.copy(description = event.description) }
            }
            is AddEditTaskEvent.EnteredCategory -> {
                _state.update { it.copy(category = event.category) }
            }
            is AddEditTaskEvent.PriorityChanged -> {
                _state.update { it.copy(priority = event.priority) }
            }
            is AddEditTaskEvent.ReminderTimeChanged -> {
                _state.update { it.copy(reminderTime = event.reminderTime) }
            }
            AddEditTaskEvent.SaveTask -> {
                saveTask()
            }
        }
    }

    private fun saveTask() {
        viewModelScope.launch {
            try {
                val currentState = _state.value
                val task = Task(
                    id = currentTaskId ?: 0,
                    title = currentState.title,
                    description = currentState.description,
                    category = currentState.category,
                    priority = currentState.priority,
                    reminderTime = currentState.reminderTime
                )
                
                if (currentTaskId == null) {
                    addTaskUseCase(task)
                } else {
                    editTaskUseCase(task)
                }
                _state.update { it.copy(isTaskSaved = true) }
            } catch (e: Exception) {
                _state.update { it.copy(error = e.message) }
            }
        }
    }
}
