package com.app.todo.business.data.main.datasource.remote

import com.app.todo.business.data.entity.TodoEntity
import com.app.todo.business.data.main.datasource.TodoDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject

class RemoteTodoDataSource @Inject constructor() : TodoDataSource {

    override suspend fun getAllTodosList(): Flow<List<TodoEntity>> {
        //TODO implement api call for fetch all todos list
        //private val todoApiService: TodoApiService
        return emptyFlow()
    }

    override suspend fun insertTodo(todoTitle: String) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTodo(todoId: Int) {
        TODO("Not yet implemented")
    }
}
