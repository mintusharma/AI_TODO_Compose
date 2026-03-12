package com.geekdroid.my_todo_ai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.geekdroid.my_todo_ai.presentation.home.HomeScreen
import com.geekdroid.my_todo_ai.presentation.task_detail.AddEditTaskScreen
import com.geekdroid.my_todo_ai.ui.theme.My_ToDo_AITheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            My_ToDo_AITheme {
                TodoNavHost()
            }
        }
    }
}

@Composable
fun TodoNavHost() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                onAddTaskClick = { navController.navigate("add_edit_task") },
                onEditTaskClick = { taskId ->
                    navController.navigate("add_edit_task?taskId=$taskId")
                }
            )
        }
        composable(
            route = "add_edit_task?taskId={taskId}",
            arguments = listOf(
                navArgument("taskId") {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) {
            AddEditTaskScreen(
                onBackClick = { navController.popBackStack() },
                onSaveSuccess = { navController.popBackStack() }
            )
        }
    }
}
