package com.app.todo.features.todoadd

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.app.todo.common.navigation.NavigationResult
import com.app.todo.common.navigation.Screen

fun NavGraphBuilder.todoAddScreen(
    navigateBack: (NavigationResult) -> Unit,
) {
    composable(route = Screen.TodoAdd.route) {
        TodoAddScreen(navigateBack = navigateBack)
    }
}

fun NavController.navigateToTodoAddScreen() {
    this.navigate(Screen.TodoAdd.route)
}
