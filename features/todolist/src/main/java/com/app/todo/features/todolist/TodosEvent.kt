package com.app.todo.features.todolist

import com.app.todo.presentation.widgets.model.TodoState

sealed class TodosEvent {
    data class DeleteTodo(val todo: TodoState) : TodosEvent()
    data class SearchTodo(val query: String) : TodosEvent()
    data class ToggleSearch(val show: Boolean) : TodosEvent()
}
