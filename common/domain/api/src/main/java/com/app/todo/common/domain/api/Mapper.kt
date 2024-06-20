package com.app.todo.common.domain.api

interface Mapper<R, E> {
    fun mapTo(type: R?): E
}
