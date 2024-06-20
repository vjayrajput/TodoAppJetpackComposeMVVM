package com.app.todo.business.domain.main.repository

import com.app.todo.business.domain.model.Todo
import kotlinx.coroutines.flow.Flow

interface TodoRepository {

    suspend fun getAllTodosList(): Flow<List<Todo>>

    suspend fun insertTodo(todoTitle: String)

    suspend fun deleteTodo(todoId: Int)
}
