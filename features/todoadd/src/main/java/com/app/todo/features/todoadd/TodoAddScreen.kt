package com.app.todo.features.todoadd

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.todo.common.navigation.NavigationResult
import com.app.todo.ui.components.TodoAppTopBar
import com.app.todo.ui.resources.strings.StringResources
import kotlinx.coroutines.flow.collectLatest

@Composable
fun TodoAddScreen(
    viewModel: TodoAddViewModel = hiltViewModel(),
    navigateBack: (NavigationResult) -> Unit,
) {
    val snackBarHostState = remember { SnackbarHostState() }
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is TodoUiEvent.ShowSnackBar -> {
                    snackBarHostState.showSnackbar(
                        message = event.message
                    )
                }

                is TodoUiEvent.SaveTodo -> {
                    navigateBack(NavigationResult.Back)
                }

                is TodoUiEvent.ErrorTodo -> {
                    navigateBack(NavigationResult.Error(event.message))
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TodoAppTopBar(
                title = stringResource(id = StringResources.titleTodoAdd),
                navigateBack = {
                    navigateBack(NavigationResult.Back)
                },
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
    ) { innerPadding ->
        AddTodoView(
            modifier = Modifier
                .padding(innerPadding),
            viewModel = viewModel
        )
    }
}

@Composable
private fun AddTodoView(modifier: Modifier = Modifier, viewModel: TodoAddViewModel) {

    val todoTitleText by viewModel.title
    val isLoading by viewModel.isLoading

    val keyboardController = LocalSoftwareKeyboardController.current
    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
        ) {
            OutlinedTextField(
                value = todoTitleText,
                onValueChange = {
                    viewModel.onEvent(TodoAddEvent.EnteredTitle(it))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                label = { Text(text = stringResource(id = StringResources.enterNewItem)) },
                textStyle = MaterialTheme.typography.bodyLarge,
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    capitalization = KeyboardCapitalization.Sentences
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                    }
                ),
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    viewModel.onEvent(TodoAddEvent.SaveTodoAdd)
                    keyboardController?.hide()
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(text = stringResource(id = StringResources.addTodo))
            }
        }

        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}
