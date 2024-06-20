package com.app.todo.features.todoadd.di

import com.app.todo.business.domain.main.usecase.AddTodoUseCase
import com.app.todo.features.todoadd.TodoAddViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object TodoAddModule {

    @Provides
    fun provideTodoAddViewModel(addTodoUseCase: AddTodoUseCase): TodoAddViewModel {
        return TodoAddViewModel(addTodoUseCase = addTodoUseCase)
    }
}
