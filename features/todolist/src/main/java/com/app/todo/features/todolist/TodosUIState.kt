package com.app.todo.features.todolist

import com.app.todo.presentation.widgets.model.TodoState


data class TodosUIState(
    val todos: List<TodoState> = emptyList(),
    val searchQuery: String = "",
    val isSearchActive: Boolean = false,
    val fabVisible: Boolean = false,
)
