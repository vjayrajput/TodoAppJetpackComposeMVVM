package com.app.todo.features.todolist

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.app.todo.common.navigation.NavigationResult
import com.app.todo.common.navigation.Screen

fun NavGraphBuilder.todoListScreen(
    onAddClick: () -> Unit,
) {
    composable(route = Screen.TodoList.route) { navBackResult ->
        val navigationResult: NavigationResult =
            navBackResult.savedStateHandle.get<NavigationResult>(NavigationResult.KEY_RESULT)
                ?: NavigationResult.Back
        TodoListScreen(
            navigationResult = navigationResult,
            onAddClick = {
                navBackResult.savedStateHandle.remove<NavigationResult>(NavigationResult.KEY_RESULT)
                onAddClick()
            }
        )
    }
}

