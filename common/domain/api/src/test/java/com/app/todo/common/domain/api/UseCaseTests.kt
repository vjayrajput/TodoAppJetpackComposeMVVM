package com.app.todo.common.domain.api

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

// Example class that implements IParams interface
class MockParams : IParams

class UseCaseTests {

    @Test
    fun testSuspendingUseCase() = runBlocking {
        // Create an instance of the use case to be tested
        val useCase = SuspendingExampleUseCase()

        // Execute the use case
        val result = useCase()

        // Assert the result
        assertEquals(42, result)
    }

    @Test
    fun testSuspendingFlowParameterizedUseCase() = runBlocking {
        // Mock dependencies or use a real instance of params
        val mockParams = MockParams()

        // Create an instance of the use case to be tested
        val useCase = SuspendingFlowParameterizedExampleUseCase()

        // Execute the use case
        val resultFlow = useCase(mockParams)

        // Collect the result from the flow in a blocking manner (for testing purposes)
        val resultList = resultFlow.toList()

        // Assert the result
        assertEquals(1, resultList.size) // Ensure only one value emitted
        assertEquals(42, resultList[0]) // Ensure emitted value is correct
    }

    @Test
    fun testSuspendingFlowUseCase() = runBlocking {
        // Create an instance of the use case to be tested
        val useCase = SuspendingFlowExampleUseCase()

        // Execute the use case
        val resultFlow = useCase()

        // Collect the result from the flow in a blocking manner (for testing purposes)
        val resultList = resultFlow.toList()

        // Assert the result
        assertEquals(1, resultList.size) // Ensure only one value emitted
        assertEquals(42, resultList[0]) // Ensure emitted value is correct
    }

    @Test
    fun testSuspendingParameterizedUseCase() = runBlocking {
        // Create an instance of the use case to be tested
        val useCase = SuspendingParameterizedUseCase()

        // Define a parameter for the use case
        val param = "Hello"

        // Execute the use case with the parameter
        val result = useCase(param)

        // Assert the result
        assertEquals(param.length, result)
    }

}

class SuspendingExampleUseCase : UseCase.Suspending<Int> {
    override suspend operator fun invoke(): Int {
        // Example implementation that returns an integer
        return 42
    }
}

class SuspendingParameterizedUseCase : UseCase.SuspendingParameterized<String, Int> {
    override suspend operator fun invoke(param: String): Int {
        // Example implementation that returns a computed integer based on the parameter
        return param.length
    }
}

class SuspendingFlowExampleUseCase : UseCase.SuspendingFlow<Int> {
    override suspend operator fun invoke(): Flow<Int> {
        // Example implementation that returns a Flow emitting an integer
        return flow {
            emit(42)
        }
    }
}

class SuspendingFlowParameterizedExampleUseCase :
    UseCase.SuspendingFlowParameterized<MockParams, Int> {
    override suspend operator fun invoke(param: MockParams): Flow<Int> {
        // Example implementation that returns a Flow
        return flow {
            emit(42)
        }
    }
}