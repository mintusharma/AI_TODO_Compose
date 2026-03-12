package com.geekdroid.my_todo_ai.presentation.home.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.geekdroid.my_todo_ai.domain.model.Priority
import com.geekdroid.my_todo_ai.domain.model.Task

@Composable
fun TaskItem(
    task: Task,
    onToggleCompletion: () -> Unit,
    onDelete: () -> Unit,
    onEdit: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = if (task.isCompleted) 0.5f else 1f)
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = task.isCompleted,
                onCheckedChange = { onToggleCompletion() }
            )
            
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            ) {
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.titleMedium,
                    textDecoration = if (task.isCompleted) TextDecoration.LineThrough else null
                )
                if (task.description.isNotBlank()) {
                    Text(
                        text = task.description,
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 2
                    )
                }
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Row(verticalAlignment = Alignment.CenterVertically) {
                    SuggestionChip(
                        onClick = { },
                        label = { Text(task.category, style = MaterialTheme.typography.labelSmall) }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    PriorityBadge(priority = task.priority)
                }
            }

            Row {
                IconButton(onClick = onEdit) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit Task")
                }
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete Task")
                }
            }
        }
    }
}

@Composable
fun PriorityBadge(priority: Priority) {
    val color = when (priority) {
        Priority.LOW -> MaterialTheme.colorScheme.primaryContainer
        Priority.MEDIUM -> MaterialTheme.colorScheme.tertiaryContainer
        Priority.HIGH -> MaterialTheme.colorScheme.errorContainer
    }
    val textColor = when (priority) {
        Priority.LOW -> MaterialTheme.colorScheme.onPrimaryContainer
        Priority.MEDIUM -> MaterialTheme.colorScheme.onTertiaryContainer
        Priority.HIGH -> MaterialTheme.colorScheme.onErrorContainer
    }

    Surface(
        color = color,
        shape = MaterialTheme.shapes.extraSmall
    ) {
        Text(
            text = priority.name,
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
            style = MaterialTheme.typography.labelSmall,
            color = textColor
        )
    }
}
