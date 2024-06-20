package com.app.todo.features.todoadd

sealed class TodoAddEvent {
    data class EnteredTitle(val value: String) : TodoAddEvent()
    object SaveTodoAdd : TodoAddEvent()
}
