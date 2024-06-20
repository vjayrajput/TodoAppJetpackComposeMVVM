package com.app.todo.business.domain.main.usecase

import com.app.todo.business.domain.main.repository.TodoRepository
import com.app.todo.business.domain.model.Todo
import com.app.todo.common.domain.api.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllTodosUseCase @Inject constructor(
    private val repository: TodoRepository
) : UseCase.SuspendingFlow<List<Todo>> {

    override suspend fun invoke(): Flow<List<Todo>> {
        return repository.getAllTodosList()
    }
}
