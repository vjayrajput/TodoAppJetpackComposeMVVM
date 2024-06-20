package com.app.todo.business.data.main.repository

import com.app.todo.business.data.main.datasource.local.LocalTodoDataSource
import com.app.todo.business.data.main.mapper.TodoEntityToTodoMapper
import com.app.todo.business.domain.main.repository.TodoRepository
import com.app.todo.business.domain.model.Todo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor(
    private val localTodoDataSource: LocalTodoDataSource,
    private val todoEntityToTodoMapper: TodoEntityToTodoMapper,
) : TodoRepository {

    override suspend fun getAllTodosList(): Flow<List<Todo>> {
        return localTodoDataSource.getAllTodosList().map { items ->
            items.map { entity -> todoEntityToTodoMapper.mapTo(entity) }
        }
    }

    override suspend fun insertTodo(todoTitle: String) {
        return localTodoDataSource.insertTodo(todoTitle)
    }

    override suspend fun deleteTodo(todoId: Int) {
        return localTodoDataSource.deleteTodo(todoId)
    }
}
