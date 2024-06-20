package com.app.todo.presentation.widgets.mapper

import com.app.todo.business.domain.model.Todo
import org.junit.Assert.assertEquals
import org.junit.Test

class TodoToUiStateMapperTest {

    private val sut = TodoToUiStateMapper()

    // Test case for mapping Todo to TodoState
    @Test
    fun testMapTodoToTodoState() {
        // Create an instance of TodoToUiStateMapper

        // Create a sample Todo object
        val todo = Todo(1, "Sample Todo", System.currentTimeMillis())

        // Perform the mapping
        val result = sut.map(todo)

        // Assert the result
        assertEquals(todo.id.toString(), result.id)
        assertEquals(todo.id, result.todoId)
        assertEquals(todo.title, result.title)
        assertEquals(todo.timestamp, result.timestamp)
    }
}
