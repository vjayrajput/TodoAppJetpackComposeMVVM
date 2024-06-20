package com.app.todo.business.data.main.datasource.local

import com.app.todo.business.data.entity.TodoEntity
import com.app.todo.business.data.main.datasource.local.dao.TodoDao
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
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
class LocalTodoDataSourceTest {

    private lateinit var localTodoDataSource: LocalTodoDataSource
    private val todoDao: TodoDao = mockk(relaxed = true)

    @Before
    fun setUp() {
        localTodoDataSource = LocalTodoDataSource(todoDao)
    }

    @Test
    fun `test getAllTodosList returns flow from todoDao`() = runTest {
        // Given
        val todoEntity1 = TodoEntity(1, "Test Todo 1", System.currentTimeMillis())
        val todoEntity2 = TodoEntity(2, "Test Todo 2", System.currentTimeMillis())
        val entities = listOf(todoEntity1, todoEntity2)
        coEvery { todoDao.getAllTodos() } returns flowOf(entities)

        // When
        val result: Flow<List<TodoEntity>> = localTodoDataSource.getAllTodosList()

        // Then
        val resultList = result.toList().first()
        assertEquals(entities, resultList)

        coVerify { todoDao.getAllTodos() }
    }

    @Test
    fun `test insertTodo calls todoDao insertTodo`() = runTest {
        // Given
        val todoTitle = "Test Todo"
        val currentTime = System.currentTimeMillis()
        val todoEntitySlot = slot<TodoEntity>()
        coEvery { todoDao.insertTodo(capture(todoEntitySlot)) } just Runs

        // When
        localTodoDataSource.insertTodo(todoTitle)

        // Then
        coVerify { todoDao.insertTodo(any()) }
        val insertedEntity = todoEntitySlot.captured
        assertEquals(todoTitle, insertedEntity.title)
        assert(insertedEntity.timestamp in currentTime..System.currentTimeMillis())
    }

    @Test
    fun `test deleteTodo calls todoDao deleteTodo`() = runTest {
        // Given
        val todoId = 1
        coEvery { todoDao.deleteTodo(todoId) } just Runs

        // When
        localTodoDataSource.deleteTodo(todoId)

        // Then
        coVerify { todoDao.deleteTodo(todoId) }
    }
}
