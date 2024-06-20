package com.app.todo.business.domain.main.usecase

import com.app.todo.business.domain.main.repository.TodoRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DeleteTodoUseCaseTest {

    private lateinit var deleteTodoUseCase: DeleteTodoUseCase
    private val todoRepository: TodoRepository = mockk(relaxed = true)

    @Before
    fun setUp() {
        deleteTodoUseCase = DeleteTodoUseCase(todoRepository)
    }

    @Test
    fun `test deleteTodoUseCase calls repository deleteTodo`() = runTest {
        // Given
        val todoId = 1
        coEvery { todoRepository.deleteTodo(todoId) } returns Unit

        // When
        deleteTodoUseCase.invoke(todoId)

        // Then
        coVerify { todoRepository.deleteTodo(todoId) }
    }
}
