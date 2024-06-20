package com.app.todo.business.data.main.datasource

import com.app.todo.business.data.entity.TodoEntity
import kotlinx.coroutines.flow.Flow

interface TodoDataSource {
    suspend fun getAllTodosList(): Flow<List<TodoEntity>>

    suspend fun insertTodo(todoTitle: String)

    suspend fun deleteTodo(todoId: Int)
}
