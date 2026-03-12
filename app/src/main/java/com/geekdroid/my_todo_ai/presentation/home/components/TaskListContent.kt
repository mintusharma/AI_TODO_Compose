package com.geekdroid.my_todo_ai.presentation.home.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.geekdroid.my_todo_ai.domain.model.Task

@Composable
fun TaskListContent(
    tasks: List<Task>,
    isGridView: Boolean,
    onToggleCompletion: (Task) -> Unit,
    onDelete: (Task) -> Unit,
    onEdit: (Task) -> Unit,
    modifier: Modifier = Modifier
) {
    AnimatedContent(
        targetState = isGridView,
        transitionSpec = {
            fadeIn().togetherWith(fadeOut())
        },
        label = "ViewModeAnimation"
    ) { targetIsGridView ->
        if (targetIsGridView) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(tasks, key = { it.id }) { task ->
                    TaskItem(
                        task = task,
                        onToggleCompletion = { onToggleCompletion(task) },
                        onDelete = { onDelete(task) },
                        onEdit = { onEdit(task) }
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(tasks, key = { it.id }) { task ->
                    TaskItem(
                        task = task,
                        onToggleCompletion = { onToggleCompletion(task) },
                        onDelete = { onDelete(task) },
                        onEdit = { onEdit(task) }
                    )
                }
            }
        }
    }
}
