package com.app.todo.business.data.main.repository

import com.app.todo.business.data.entity.TodoEntity
import com.app.todo.business.data.main.datasource.local.LocalTodoDataSource
import com.app.todo.business.data.main.mapper.TodoEntityToTodoMapper
import com.app.todo.business.domain.model.Todo
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class TodoRepositoryImplTest {

    private lateinit var repository: TodoRepositoryImpl
    private val localTodoDataSource: LocalTodoDataSource = mockk(relaxed = true)
    private val todoEntityToTodoMapper: TodoEntityToTodoMapper = mockk(relaxed = true)

    @Before
    fun setUp() {
        repository = TodoRepositoryImpl(localTodoDataSource, todoEntityToTodoMapper)
    }

    @Test
    fun `test getAllTodosList returns mapped list`() = runTest {
        // Given
        val todoEntity1 = mockk<TodoEntity>(relaxed = true)
        val todoEntity2 = mockk<TodoEntity>(relaxed = true)
        val todo1 = mockk<Todo>(relaxed = true)
        val todo2 = mockk<Todo>(relaxed = true)
        val entities = listOf(todoEntity1, todoEntity2)
        val expectedTodos = listOf(todo1, todo2)

        coEvery { localTodoDataSource.getAllTodosList() } returns flowOf(entities)
        every { todoEntityToTodoMapper.mapTo(todoEntity1) } returns todo1
        every { todoEntityToTodoMapper.mapTo(todoEntity2) } returns todo2

        // When
        val result: Flow<List<Todo>> = repository.getAllTodosList()

        // Then
        val resultList = result.toList().first()
        assertEquals(expectedTodos, resultList)

        coVerify { localTodoDataSource.getAllTodosList() }
        verify { todoEntityToTodoMapper.mapTo(todoEntity1) }
        verify { todoEntityToTodoMapper.mapTo(todoEntity2) }
    }

    @Test
    fun `test insertTodo calls localTodoDataSource insertTodo`() = runTest {
        // Given
        val todoTitle = "Test Todo"
        coEvery { localTodoDataSource.insertTodo(todoTitle) } just Runs

        // When
        repository.insertTodo(todoTitle)

        // Then
        coVerify { localTodoDataSource.insertTodo(todoTitle) }
    }

    @Test
    fun `test deleteTodo calls localTodoDataSource deleteTodo`() = runTest {
        // Given
        val todoId = 1
        coEvery { localTodoDataSource.deleteTodo(todoId) } just Runs

        // When
        repository.deleteTodo(todoId)

        // Then
        coVerify { localTodoDataSource.deleteTodo(todoId) }
    }
}
