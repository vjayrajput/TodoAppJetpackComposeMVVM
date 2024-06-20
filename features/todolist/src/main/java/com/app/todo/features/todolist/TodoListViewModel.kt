package com.app.todo.features.todolist

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.todo.business.domain.main.usecase.DeleteTodoUseCase
import com.app.todo.business.domain.main.usecase.GetAllTodosUseCase
import com.app.todo.presentation.widgets.mapper.TodoToUiStateMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val getAllTodosUseCase: GetAllTodosUseCase,
    private val deleteTodoUseCase: DeleteTodoUseCase,
    private val todoToUiStateMapper: TodoToUiStateMapper,
) : ViewModel() {

    private val _state = mutableStateOf(TodosUIState())
    val state: State<TodosUIState> = _state


    private var searchText by mutableStateOf("")

    private var getTodosJob: Job? = null

    init {
        getTodos()
    }

    fun onEvent(event: TodosEvent) {
        when (event) {
            is TodosEvent.DeleteTodo -> {
                viewModelScope.launch {
                    deleteTodoUseCase.invoke(event.todo.todoId)
                }
            }

            is TodosEvent.SearchTodo -> {
                searchText = event.query
                _state.value = state.value.copy(searchQuery = event.query)
                getTodosDebounced(event.query)
            }

            is TodosEvent.ToggleSearch -> {
                _state.value = state.value.copy(isSearchActive = event.show, searchQuery = "")
            }
        }
    }

    private fun getTodosDebounced(query: String) {
        // Cancel existing job to avoid redundant calls
        getTodosJob?.cancel()

        // Debounce search with a time window of 300ms
        getTodosJob = viewModelScope.launch {
            delay(200) // Debounce time
            getTodos(query)
        }
    }

    private fun getTodos(query: String = "") {
        viewModelScope.launch {
            getAllTodosUseCase.invoke().onEach { todos ->
                val todoStates = todos.map { todo ->
                    todoToUiStateMapper.map(todo)
                }
                _state.value = state.value.copy(
                    todos = if (query.isBlank()) {
                        todoStates
                    } else {
                        todoStates.filter {
                            it.title.contains(query, ignoreCase = true)
                        }
                    }
                )
            }.launchIn(viewModelScope)
        }
    }

    override fun onCleared() {
        super.onCleared()
        getTodosJob?.cancel() // Cancel job on ViewModel cleared
    }
}
