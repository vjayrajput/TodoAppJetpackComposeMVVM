package com.app.todo.features.todolist.di

import com.app.todo.business.domain.main.usecase.DeleteTodoUseCase
import com.app.todo.business.domain.main.usecase.GetAllTodosUseCase
import com.app.todo.features.todolist.TodoListViewModel
import com.app.todo.presentation.widgets.mapper.TodoToUiStateMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object TodoListModule {

    @Provides
    fun provideTodoListViewModel(
        getAllTodosUseCase: GetAllTodosUseCase,
        deleteTodoUseCase: DeleteTodoUseCase,
        todoToUiStateMapper: TodoToUiStateMapper,
    ): TodoListViewModel {
        return TodoListViewModel(
            getAllTodosUseCase = getAllTodosUseCase,
            deleteTodoUseCase = deleteTodoUseCase,
            todoToUiStateMapper = todoToUiStateMapper
        )
    }
}
