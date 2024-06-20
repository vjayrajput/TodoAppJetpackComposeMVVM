package com.app.todo.features.todoadd

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.todo.business.domain.main.usecase.AddTodoUseCase
import com.app.todo.business.domain.main.usecase.InvalidTodoException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoAddViewModel @Inject constructor(
    private val addTodoUseCase: AddTodoUseCase,
) : ViewModel() {

    private val _title = mutableStateOf("")
    val title: State<String> = _title

    private val _eventFlow = MutableSharedFlow<TodoUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    fun onEvent(event: TodoAddEvent) {
        when (event) {

            is TodoAddEvent.EnteredTitle -> {
                _title.value = event.value
            }

            is TodoAddEvent.SaveTodoAdd -> {
                viewModelScope.launch {
                    if (title.value.isBlank()) {
                        _eventFlow.emit(
                            TodoUiEvent.ShowSnackBar(
                                message = "Title cannot be empty"
                            )
                        )
                        return@launch
                    }
                    _isLoading.value = true
                    // Show loader for 3 seconds
                    try {
                        addTodoUseCase.invoke(title.value)
                        delay(3000)
                        _eventFlow.emit(TodoUiEvent.SaveTodo)
                    } catch (e: InvalidTodoException) {
                        delay(1000)
                        _eventFlow.emit(
                            TodoUiEvent.ErrorTodo(
                                message = e.message ?: "Something went wrong"
                            )
                        )
                    } catch (e: Exception) {
                        _eventFlow.emit(
                            TodoUiEvent.ErrorTodo(
                                message = e.message ?: "Something went wrong"
                            )
                        )
                    } finally {
                        _isLoading.value = false
                    }
                }
            }
        }
    }
}
