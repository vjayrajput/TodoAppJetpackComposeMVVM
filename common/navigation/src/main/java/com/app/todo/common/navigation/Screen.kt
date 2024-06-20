package com.app.todo.common.navigation

sealed class Screen(val route: String) {
    object TodoList : Screen("todo_list_screen")
    object TodoAdd : Screen("todo_add_screen")
    object TodoDetail : Screen("todo_detail_screen")
}
