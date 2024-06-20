package com.app.todo.features.todoadd

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.app.todo.business.domain.main.usecase.AddTodoUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class TodoAddViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val addTodoUseCase: AddTodoUseCase = mockk<AddTodoUseCase>(relaxed = true)

    private lateinit var sut: TodoAddViewModel

    private val testDispatcher = StandardTestDispatcher()

    private val testScope = TestScope(testDispatcher)

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        sut = TodoAddViewModel(addTodoUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Given EnteredTitle event , Then title will be updated`() = runTest {
        val title = "New Todo Title"

        // Simulate entering a title
        sut.onEvent(TodoAddEvent.EnteredTitle(title))

        // Assert that the title state is updated
        assertEquals(title, sut.title.value)
    }

    @Test
    fun `Given SaveTodoAdd event and title is empty , Then ShowSnackBar UI event will be emitted`() =
        runTest {
            //Given
            sut.onEvent(TodoAddEvent.EnteredTitle(""))

            // When
            sut.onEvent(TodoAddEvent.SaveTodoAdd)

            // Then
            coVerify(exactly = 0) { addTodoUseCase.invoke(any()) }
            testScope.launch {
                sut.eventFlow.first().let {
                    assertEquals(it, TodoUiEvent.ShowSnackBar("Title cannot be empty"))
                }
            }
        }

    @Test
    fun `Given SaveTodoAdd event with valid title, Then addTodoUseCase will be called`() = runTest {

        // Given
        val title = "New Todo"
        sut.onEvent(TodoAddEvent.EnteredTitle(title))

        coEvery { addTodoUseCase.invoke(title) } returns Unit

        // When
        testScope.launch {
            sut.onEvent(TodoAddEvent.SaveTodoAdd)

            coVerify { addTodoUseCase.invoke(any()) }

            sut.eventFlow.first().let {
                assertEquals(it, TodoUiEvent.SaveTodo)
            }
        }
    }

    @Test
    fun `Given SaveTodoAdd event with invalid title, Then addTodoUseCase will be thrown exception`() =
        runTest {

            // Given
            val title = "Error"
            sut.onEvent(TodoAddEvent.EnteredTitle(title))

            coEvery { addTodoUseCase.invoke(title) } returns Unit

            // When
            testScope.launch {
                sut.onEvent(TodoAddEvent.SaveTodoAdd)

                coVerify { addTodoUseCase.invoke(any()) }

                sut.eventFlow.first().let {
                    assertEquals(it, TodoUiEvent.ErrorTodo("Failed to add TODO"))
                }
            }
        }

    @Test
    fun `Given SaveTodoAdd event with valid title, Then isLoading will be updated`() = runTest {

        // Given
        val title = "New Todo"
        sut.onEvent(TodoAddEvent.EnteredTitle(title))

        coEvery { addTodoUseCase.invoke(title) } returns Unit

        sut.onEvent(TodoAddEvent.SaveTodoAdd)

        // When
        assertEquals(false, sut.isLoading.value)
        advanceTimeBy(500)
        assertEquals(true, sut.isLoading.value)
        advanceTimeBy(3000)
        assertEquals(false, sut.isLoading.value)
    }
}