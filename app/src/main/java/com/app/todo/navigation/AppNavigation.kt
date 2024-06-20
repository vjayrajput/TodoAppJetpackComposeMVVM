package com.app.todo.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.app.todo.common.navigation.NavigationResult
import com.app.todo.common.navigation.Screen
import com.app.todo.features.todoadd.navigateToTodoAddScreen
import com.app.todo.features.todoadd.todoAddScreen
import com.app.todo.features.todolist.todoListScreen

@Composable
fun AppNavigation(
    modifier: Modifier,
    navController: NavHostController
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screen.TodoList.route,
    ) {
        todoListScreen(onAddClick = navController::navigateToTodoAddScreen)
        todoAddScreen(navigateBack = { result ->
            navController.previousBackStackEntry?.savedStateHandle?.set(
                NavigationResult.KEY_RESULT,
                result
            )
            navController.popBackStack()
        })
    }
}
