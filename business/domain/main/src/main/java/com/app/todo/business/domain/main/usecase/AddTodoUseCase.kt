package com.app.todo.business.domain.main.usecase

import com.app.todo.business.domain.main.repository.TodoRepository
import com.app.todo.common.domain.api.UseCase
import javax.inject.Inject

class AddTodoUseCase @Inject constructor(
    private val repository: TodoRepository
) : UseCase.SuspendingParameterized<String, Unit> {

    override suspend fun invoke(param: String) {
        /**
         * As per case we are throwing exception when
         * title is "Error" or  "error"
         */
        if (param.equals("Error", ignoreCase = true)) {
            throw InvalidTodoException("Failed to add TODO")
        }
        return repository.insertTodo(param)
    }
}

class InvalidTodoException(message: String) : Exception(message)
