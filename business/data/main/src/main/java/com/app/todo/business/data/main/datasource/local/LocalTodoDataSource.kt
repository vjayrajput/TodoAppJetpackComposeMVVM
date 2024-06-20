package com.app.todo.business.data.main.datasource.local

import com.app.todo.business.data.entity.TodoEntity
import com.app.todo.business.data.main.datasource.TodoDataSource
import com.app.todo.business.data.main.datasource.local.dao.TodoDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalTodoDataSource @Inject constructor(
    private val todoDao: TodoDao,
) : TodoDataSource {

    override suspend fun getAllTodosList(): Flow<List<TodoEntity>> {
        return todoDao.getAllTodos()
    }

    override suspend fun insertTodo(todoTitle: String) {
        val entity = TodoEntity(
            title = todoTitle,
            timestamp = System.currentTimeMillis(),
        )
        todoDao.insertTodo(entity)
    }

    override suspend fun deleteTodo(todoId: Int) {
        todoDao.deleteTodo(todoId)
    }
}
