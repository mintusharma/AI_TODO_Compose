package com.geekdroid.my_todo_ai.presentation.task_detail

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.geekdroid.my_todo_ai.domain.model.Priority
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditTaskScreen(
    onBackClick: () -> Unit,
    onSaveSuccess: () -> Unit,
    viewModel: AddEditTaskViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    
    // Permission handling for notifications (Android 13+) using standard API
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { _ -> }

    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    LaunchedEffect(state.isTaskSaved) {
        if (state.isTaskSaved) {
            onSaveSuccess()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (state.title.isEmpty() && !state.isLoading) "New Task" else "Edit Task") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.onEvent(AddEditTaskEvent.SaveTask) }) {
                        Icon(Icons.Default.Save, contentDescription = "Save Task")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = state.title,
                onValueChange = { viewModel.onEvent(AddEditTaskEvent.EnteredTitle(it)) },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = state.description,
                onValueChange = { viewModel.onEvent(AddEditTaskEvent.EnteredDescription(it)) },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            OutlinedTextField(
                value = state.category,
                onValueChange = { viewModel.onEvent(AddEditTaskEvent.EnteredCategory(it)) },
                label = { Text("Category (e.g., Work, Personal)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            PriorityDropdown(
                selectedPriority = state.priority,
                onPrioritySelected = { viewModel.onEvent(AddEditTaskEvent.PriorityChanged(it)) }
            )

            ReminderPicker(
                reminderTime = state.reminderTime,
                onTimeSelected = { viewModel.onEvent(AddEditTaskEvent.ReminderTimeChanged(it)) }
            )
            
            state.error?.let {
                Text(text = it, color = MaterialTheme.colorScheme.error)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PriorityDropdown(
    selectedPriority: Priority,
    onPrioritySelected: (Priority) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = selectedPriority.name,
            onValueChange = {},
            readOnly = true,
            label = { Text("Priority") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                .fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            Priority.entries.forEach { priority ->
                DropdownMenuItem(
                    text = { Text(priority.name) },
                    onClick = {
                        onPrioritySelected(priority)
                        expanded = false
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReminderPicker(
    reminderTime: LocalDateTime?,
    onTimeSelected: (LocalDateTime?) -> Unit
) {
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    var pickedDate by remember { mutableStateOf(LocalDate.now()) }

    val datePickerState = rememberDatePickerState()
    val timePickerState = rememberTimePickerState()

    val formatter = remember { DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm") }

    Column {
        Text("Reminder", style = MaterialTheme.typography.labelLarge)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showDatePicker = true }
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.DateRange, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = reminderTime?.format(formatter) ?: "Set Reminder",
                style = MaterialTheme.typography.bodyLarge
            )
        }
        if (reminderTime != null) {
            TextButton(onClick = { onTimeSelected(null) }) {
                Text("Clear Reminder")
            }
        }
    }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    val selectedDateMillis = datePickerState.selectedDateMillis ?: System.currentTimeMillis()
                    pickedDate = Instant.ofEpochMilli(selectedDateMillis)
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate()
                    showDatePicker = false
                    showTimePicker = true
                }) {
                    Text("OK")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    if (showTimePicker) {
        TimePickerDialog(
            onDismissRequest = { showTimePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    val pickedTime = LocalTime.of(timePickerState.hour, timePickerState.minute)
                    onTimeSelected(LocalDateTime.of(pickedDate, pickedTime))
                    showTimePicker = false
                }) {
                    Text("OK")
                }
            }
        ) {
            TimePicker(state = timePickerState)
        }
    }
}

@Composable
fun TimePickerDialog(
    onDismissRequest: () -> Unit,
    confirmButton: @Composable () -> Unit,
    content: @Composable () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = confirmButton,
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text("Cancel")
            }
        },
        text = { content() }
    )
}
