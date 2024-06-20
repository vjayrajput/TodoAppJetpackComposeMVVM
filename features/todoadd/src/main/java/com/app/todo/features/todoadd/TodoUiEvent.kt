package com.app.todo.features.todoadd

sealed class TodoUiEvent {

    data class ShowSnackBar(val message: String) : TodoUiEvent()

    object SaveTodo : TodoUiEvent()

    data class ErrorTodo(val message: String) : TodoUiEvent()
}
