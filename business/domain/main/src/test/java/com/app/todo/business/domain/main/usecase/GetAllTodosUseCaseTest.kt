package com.app.todo.business.domain.main.usecase

import com.app.todo.business.domain.main.repository.TodoRepository
import com.app.todo.business.domain.model.Todo
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
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
class GetAllTodosUseCaseTest {

    private lateinit var getAllTodosUseCase: GetAllTodosUseCase
    private val todoRepository: TodoRepository = mockk(relaxed = true)

    @Before
    fun setUp() {
        getAllTodosUseCase = GetAllTodosUseCase(todoRepository)
    }

    @Test
    fun `test getAllTodosUseCase returns todos list`() = runTest {
        // Given
        val todo1 = Todo(1, "Test Todo 1", System.currentTimeMillis())
        val todo2 = Todo(2, "Test Todo 2", System.currentTimeMillis())
        val todos = listOf(todo1, todo2)
        coEvery { todoRepository.getAllTodosList() } returns flowOf(todos)

        // When
        val result: Flow<List<Todo>> = getAllTodosUseCase.invoke()

        // Then
        val resultList = result.toList().first()
        assertEquals(todos, resultList)

        coVerify { todoRepository.getAllTodosList() }
    }
}
