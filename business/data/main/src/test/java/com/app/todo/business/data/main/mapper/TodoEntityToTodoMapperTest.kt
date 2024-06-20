package com.app.todo.business.data.main.mapper

import com.app.todo.business.data.entity.TodoEntity
import org.junit.Assert.assertEquals
import org.junit.Test

class TodoEntityToTodoMapperTest {
    private val sut = TodoEntityToTodoMapper()

    @Test
    fun `Given a valid TodoEntity When mapping is done Then a valid Todo should be created`() {

        // Given
        val todoEntity = TodoEntity(
            id = 123,
            title = "title",
            timestamp = 3455464,
        )

        // When
        val todoModel = sut.mapTo(todoEntity)

        // Then
        assertEquals(todoEntity.id, todoModel.id)
        assertEquals(todoEntity.title, todoModel.title)
        assertEquals(todoEntity.timestamp, todoModel.timestamp)
    }
}
