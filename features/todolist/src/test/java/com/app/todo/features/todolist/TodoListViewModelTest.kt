package com.app.todo.features.todolist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.app.todo.business.domain.main.usecase.DeleteTodoUseCase
import com.app.todo.business.domain.main.usecase.GetAllTodosUseCase
import com.app.todo.business.domain.model.Todo
import com.app.todo.presentation.widgets.mapper.TodoToUiStateMapper
import com.app.todo.presentation.widgets.model.TodoState
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
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
class TodoListViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val getAllTodosUseCase: GetAllTodosUseCase = mockk<GetAllTodosUseCase>(relaxed = true)
    private val deleteTodoUseCase: DeleteTodoUseCase = mockk<DeleteTodoUseCase>(relaxed = true)
    private val todoToUiStateMapper: TodoToUiStateMapper =
        mockk<TodoToUiStateMapper>(relaxed = true)

    private lateinit var sut: TodoListViewModel

    private val testDispatcher = StandardTestDispatcher()

    private val testScope = TestScope(testDispatcher)

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        //setting default todos list here
        coEvery { getAllTodosUseCase.invoke() } returns flow { dummyTodosList() }
        sut = TodoListViewModel(
            getAllTodosUseCase = getAllTodosUseCase,
            deleteTodoUseCase = deleteTodoUseCase, todoToUiStateMapper = todoToUiStateMapper
        )
        sut.onEvent(TodosEvent.SearchTodo(""))

    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Given DeleteTodo event , Then onEvent handles DeleteTodo event`() = runTest {
        // Setup
        val todoId = 123
        coEvery { deleteTodoUseCase.invoke(todoId) } returns Unit

        val deleteTodoEvent = TodosEvent.DeleteTodo(TodoState(todoId = todoId))

        // Action
        sut.onEvent(deleteTodoEvent)

        testScope.runTest {
            coVerify { deleteTodoUseCase.invoke(todoId) }
        }
    }

    @Test
    fun `Given ToggleSearch event , Then onEvent handles ToggleSearch event`() {
        // Setup
        val showSearch = true
        val toggleSearchEvent = TodosEvent.ToggleSearch(showSearch)

        // Action
        sut.onEvent(toggleSearchEvent)

        // Verification
        // Verify that state values are updated correctly
        assertEquals(showSearch, sut.state.value.isSearchActive)
        assertEquals("", sut.state.value.searchQuery) // Ensure searchQuery is reset
    }

    @Test
    fun `Given SearchTodo event , Then onEvent handles SearchTodo event`() = runTest {

        val todosList = dummyTodosList()
        coEvery { getAllTodosUseCase.invoke() } returns flow { todosList }

        testScope.launch {

            assertEquals(sut.state.value.todos, todosList.map { todoToUiStateMapper.map(it) })

            sut.onEvent(TodosEvent.SearchTodo("Wash"))

            val filteredTodos = todosList.filter { it.title.contains("Wash", ignoreCase = true) }

            assertEquals(sut.state.value.todos, filteredTodos.map { todoToUiStateMapper.map(it) })

            sut.onEvent(TodosEvent.SearchTodo(""))

            assertEquals(sut.state.value.todos, todosList.map { todoToUiStateMapper.map(it) })
        }
    }

    private fun dummyTodosList(): List<Todo> {
        return listOf(
            Todo(id = 1, title = "Buy Milk", timestamp = 123),
            Todo(id = 2, title = "Wash Car", timestamp = 234),
            Todo(id = 3, title = "Clean House", timestamp = 345),
        )
    }
}
