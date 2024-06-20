package com.app.todo.presentation.widgets.mapper

import com.app.todo.business.domain.model.Todo
import com.app.todo.presentation.widgets.model.TodoState
import javax.inject.Inject

class TodoToUiStateMapper @Inject constructor() {
    fun map(param: Todo): TodoState {
        return TodoState(
            id = param.id.toString(),
            todoId = param.id,
            title = param.title,
            timestamp = param.timestamp,
        )
    }
}
