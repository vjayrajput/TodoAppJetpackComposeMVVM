package com.app.todo.common.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.app.todo.common.general.extensions.currentRoute
import com.app.todo.ui.resources.strings.StringResources

@Composable
fun NavController.navigationTitle(): String {
    return when (this.currentRoute()) {
        Screen.TodoAdd.route -> stringResource(StringResources.titleTodoAdd)
        else -> {
            stringResource(StringResources.appTitle)
        }
    }
}
