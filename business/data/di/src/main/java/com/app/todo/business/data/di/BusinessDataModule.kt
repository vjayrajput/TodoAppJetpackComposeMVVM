package com.app.todo.business.data.di

import android.app.Application
import androidx.room.Room
import com.app.todo.business.data.main.datasource.local.LocalTodoDataSource
import com.app.todo.business.data.main.datasource.local.dao.TodoDao
import com.app.todo.business.data.main.datasource.local.database.TodoAppDatabase
import com.app.todo.business.data.main.mapper.TodoEntityToTodoMapper
import com.app.todo.business.data.main.repository.TodoRepositoryImpl
import com.app.todo.business.domain.main.repository.TodoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class BusinessDataModule {


    @Provides
    @Singleton
    fun provideTodoDatabase(app: Application): TodoAppDatabase {
        return Room.databaseBuilder(
            app,
            TodoAppDatabase::class.java,
            TodoAppDatabase.DATABASE_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun provideTodoRepository(
        localTodoDataSource: LocalTodoDataSource,
        todoEntityToTodoMapper: TodoEntityToTodoMapper,
    ): TodoRepository {
        return TodoRepositoryImpl(
            localTodoDataSource = localTodoDataSource,
            todoEntityToTodoMapper = todoEntityToTodoMapper,
        )
    }

    @Singleton
    @Provides
    fun provideLocalTodoDataSource(
        todoDao: TodoDao
    ): LocalTodoDataSource {
        return LocalTodoDataSource(
            todoDao = todoDao,
        )
    }

    @Provides
    fun provideTodoDao(database: TodoAppDatabase): TodoDao = database.todoDao()

}
