package com.app.todo.features.todolist


import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.todo.common.navigation.NavigationResult
import com.app.todo.ui.components.EmptyListView
import com.app.todo.ui.components.ErrorAlertDialog
import com.app.todo.ui.components.TodoCard
import com.app.todo.ui.components.TodoListFab
import com.app.todo.ui.components.TodoSearchTextField
import com.app.todo.ui.resources.strings.StringResources


@Composable
fun TodoListScreen(
    viewModel: TodoListViewModel = hiltViewModel(),
    navigationResult: NavigationResult,
    onAddClick: () -> Unit,
) {
    val listState = rememberLazyListState()
    var fabVisible by remember { mutableStateOf(true) }

    val todos = viewModel.state.value.todos

    val snackBarHostState = remember { SnackbarHostState() }

    val searchText = viewModel.state.value.searchQuery
    val openDialog = remember { mutableStateOf(false) }
    val errorMessage = remember { mutableStateOf("") }
    // Track the scroll position
    LaunchedEffect(listState) {
        var previousIndex = listState.firstVisibleItemIndex
        var previousScrollOffset = listState.firstVisibleItemScrollOffset

        snapshotFlow { listState.firstVisibleItemIndex to listState.firstVisibleItemScrollOffset }
            .collect { (index, scrollOffset) ->
                fabVisible = when {
                    index > previousIndex -> false
                    index < previousIndex -> true
                    scrollOffset > previousScrollOffset -> false
                    else -> true
                }
                previousIndex = index
                previousScrollOffset = scrollOffset
            }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
        topBar = {
            TodoListTopBar(
                searchText = viewModel.state.value.searchQuery,
                onSearchClicked = { viewModel.onEvent(TodosEvent.ToggleSearch(it)) },
                isSearchActive = viewModel.state.value.isSearchActive,
                onSearchTextChanged = { viewModel.onEvent(TodosEvent.SearchTodo(it)) }
            )
        },
        floatingActionButton = {
            TodoListFab(
                visible = fabVisible,
                onAddClick = onAddClick
            )
        }
    ) { innerPadding ->
        when {
            todos.isEmpty() && searchText.isEmpty() -> {
                EmptyListView(
                    modifier = Modifier.padding(innerPadding),
                    message = stringResource(id = StringResources.pressAddButtonToAddTodoItem)
                )
            }

            todos.isEmpty() -> {
                EmptyListView(
                    modifier = Modifier.padding(innerPadding),
                    message = stringResource(id = StringResources.noSearchResultsFound)
                )
            }

            else -> {
                TodoItemListView(
                    modifier = Modifier.padding(innerPadding),
                    viewModel = viewModel,
                    listState = listState,
                )
            }
        }
        if (openDialog.value) {
            ErrorAlertDialog(
                message = errorMessage.value,
                okButtonClick = {
                    openDialog.value = false
                })
        }
    }
    if (navigationResult is NavigationResult.Error) {
        LaunchedEffect(Unit) {
            openDialog.value = true
            errorMessage.value = navigationResult.message
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TodoListTopBar(
    searchText: String,
    onSearchClicked: (Boolean) -> Unit,
    isSearchActive: Boolean,
    onSearchTextChanged: (String) -> Unit
) {
    if (isSearchActive) {
        CenterAlignedTopAppBar(
            title = {
                TodoSearchTextField(
                    text = searchText,
                    onValueChange = onSearchTextChanged,
                    onSearchCloseClicked = {
                        onSearchClicked(false)
                        onSearchTextChanged("")
                    }
                )
            },
        )
    } else {
        TopAppBar(
            title = { Text(text = stringResource(id = StringResources.appTitle)) },
            colors = TopAppBarDefaults.topAppBarColors()
                .copy(containerColor = MaterialTheme.colorScheme.primaryContainer),
            actions = {
                IconButton(onClick = { onSearchClicked(true) }) {
                    Icon(Icons.Filled.Search, contentDescription = "Search")
                }
            }
        )
    }
}

@Composable
private fun TodoItemListView(
    modifier: Modifier,
    viewModel: TodoListViewModel,
    listState: LazyListState
) {
    val todos = viewModel.state.value.todos
    LazyColumn(
        modifier = modifier
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
            .fillMaxSize(),
        state = listState,
    ) {
        items(todos) { todoItem ->
            TodoCard(todoItem = todoItem, onDeleteClick = {
                viewModel.onEvent(TodosEvent.DeleteTodo(todoItem))
            })
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
