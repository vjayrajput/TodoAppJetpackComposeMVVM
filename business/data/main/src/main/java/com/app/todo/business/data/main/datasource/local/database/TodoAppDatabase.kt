package com.app.todo.business.data.main.datasource.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.todo.business.data.entity.TodoEntity
import com.app.todo.business.data.main.datasource.local.dao.TodoDao


@Database(entities = [TodoEntity::class], version = 1)
abstract class TodoAppDatabase : RoomDatabase() {

    abstract fun todoDao(): TodoDao

    companion object {
        const val DATABASE_NAME = "todos_db"
    }
}