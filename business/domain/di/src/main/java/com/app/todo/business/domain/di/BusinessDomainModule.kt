package com.app.todo.business.domain.di

import com.app.todo.business.domain.main.repository.TodoRepository
import com.app.todo.business.domain.main.usecase.AddTodoUseCase
import com.app.todo.business.domain.main.usecase.DeleteTodoUseCase
import com.app.todo.business.domain.main.usecase.GetAllTodosUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object BusinessDomainModule {

    @Provides
    fun provideAddTodoUseCase(repository: TodoRepository) =
        AddTodoUseCase(repository)

    @Provides
    fun provideDeleteTodoUseCase(repository: TodoRepository) =
        DeleteTodoUseCase(repository)

    @Provides
    fun provideGetAllTodosUseCase(repository: TodoRepository) =
        GetAllTodosUseCase(repository)

}
