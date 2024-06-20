package com.app.todo.business.domain.main.usecase

import com.app.todo.business.domain.main.repository.TodoRepository
import com.app.todo.common.domain.api.UseCase
import javax.inject.Inject

class DeleteTodoUseCase @Inject constructor(
    private val repository: TodoRepository
) : UseCase.SuspendingParameterized<Int, Unit> {

    override suspend fun invoke(param: Int) {
        return repository.deleteTodo(param)
    }
}
