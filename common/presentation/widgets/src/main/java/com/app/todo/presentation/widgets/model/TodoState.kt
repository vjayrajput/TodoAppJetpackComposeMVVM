package com.app.todo.presentation.widgets.model

import com.app.todo.common.general.models.State

data class TodoState(
    override val id: String = "",
    val todoId: Int = 0,
    val title: String = "",
    val timestamp: Long = 0,
) : State {

    companion object {
        val EMPTY = TodoState()
    }
}
