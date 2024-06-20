package com.app.todo.business.data.main.mapper

import com.app.todo.business.data.entity.TodoEntity
import com.app.todo.business.domain.model.Todo
import com.app.todo.common.domain.api.Mapper
import javax.inject.Inject

class TodoEntityToTodoMapper @Inject constructor() : Mapper<TodoEntity, Todo> {

    override fun mapTo(type: TodoEntity?): Todo {
        return Todo(
            id = type?.id ?: 0,
            title = type?.title.toString(),
            timestamp = type?.timestamp ?: 0,
        )
    }
}
