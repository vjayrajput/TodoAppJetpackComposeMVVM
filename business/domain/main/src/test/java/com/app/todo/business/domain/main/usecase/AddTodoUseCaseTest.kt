package com.app.todo.business.domain.main.usecase

import com.app.todo.business.domain.main.repository.TodoRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class AddTodoUseCaseTest {

    private lateinit var addTodoUseCase: AddTodoUseCase
    private val todoRepository: TodoRepository = mockk(relaxed = true)

    @Before
    fun setUp() {
        addTodoUseCase = AddTodoUseCase(todoRepository)
    }

    @Test
    fun `test addTodoUseCase throws exception for invalid title`() = runTest {
        // Given
        val invalidTitle = "Error"

        // When & Then
        val exception = assertFailsWith<InvalidTodoException> {
            addTodoUseCase.invoke(invalidTitle)
        }
        assertEquals("Failed to add TODO", exception.message)
    }

    @Test
    fun `test addTodoUseCase calls repository insertTodo for valid title`() = runTest {
        // Given
        val validTitle = "Valid Title"
        coEvery { todoRepository.insertTodo(validTitle) } returns Unit

        // When
        addTodoUseCase.invoke(validTitle)

        // Then
        coEvery { todoRepository.insertTodo(validTitle) }
    }
}
