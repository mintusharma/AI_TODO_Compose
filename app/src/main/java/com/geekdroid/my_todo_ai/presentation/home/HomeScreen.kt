package com.geekdroid.my_todo_ai.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ViewList
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.geekdroid.my_todo_ai.presentation.home.components.TaskListContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onAddTaskClick: () -> Unit,
    onEditTaskClick: (Int) -> Unit,
    viewModel: TaskViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("My To-Do AI", style = MaterialTheme.typography.headlineMedium) },
                actions = {
                    IconButton(onClick = { viewModel.onEvent(TaskEvent.ToggleViewMode) }) {
                        Icon(
                            imageVector = if (state.isGridView) Icons.AutoMirrored.Filled.ViewList else Icons.Default.GridView,
                            contentDescription = "Toggle View Mode"
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    scrolledContainerColor = MaterialTheme.colorScheme.surfaceContainer
                )
            )
        },
        floatingActionButton = {
            LargeFloatingActionButton(
                onClick = onAddTaskClick,
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.padding(bottom = 16.dp, end = 16.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Task")
            }
        },
        contentWindowInsets = WindowInsets.safeDrawing
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            TaskListContent(
                tasks = state.tasks,
                isGridView = state.isGridView,
                onToggleCompletion = { viewModel.onEvent(TaskEvent.ToggleTaskCompletion(it)) },
                onDelete = { viewModel.onEvent(TaskEvent.DeleteTask(it)) },
                onEdit = { onEditTaskClick(it.id) }
            )
            
            if (state.tasks.isEmpty() && !state.isLoading) {
                Text(
                    text = "No tasks found",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.align(androidx.compose.ui.Alignment.Center)
                )
            }
            
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(androidx.compose.ui.Alignment.Center))
            }
        }
    }
}
