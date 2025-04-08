package lv.chernishenko.chucknorrisfacts.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import lv.chernishenko.chucknorrisfacts.ui.listitem.ChuckNorrisFactListItem
import lv.chernishenko.chucknorrisfacts.ui.theme.ChuckNorrisFactsTheme
import lv.chernishenko.chucknorrisfacts.viewmodel.ChuckNorrisViewModel
import lv.chernishenko.chucknorrisfacts.viewmodel.MainScreenState.Error
import lv.chernishenko.chucknorrisfacts.viewmodel.MainScreenState.Loading
import lv.chernishenko.chucknorrisfacts.viewmodel.MainScreenState.Success

@Composable
fun StartScreen(
    viewModel: ChuckNorrisViewModel = hiltViewModel(),
    onItemClick: (String) -> Unit,
) {
    val localFacts = viewModel.pager.collectAsLazyPagingItems()
    val state = viewModel.uiState.collectAsState()
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    if (state.value is Error) {
        LaunchedEffect(key1 = Unit) {
            scope.launch {
                val result = snackbarHostState.showSnackbar(
                    message = (state.value as Error).message,
                    actionLabel = "Retry",
                    withDismissAction = true
                )
                when (result) {
                    SnackbarResult.ActionPerformed -> {
                        viewModel.getRandomFact()
                    }
                    SnackbarResult.Dismissed -> {
                        // Do nothing
                    }
                }
            }
        }
    }

    ChuckNorrisFactsTheme {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.surface),
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        if (state.value is Loading) return@FloatingActionButton
                        viewModel.getRandomFact()
                    }
                ) {
                    if (state.value is Loading) {
                        CircularProgressIndicator()
                    } else {
                        Icon(Icons.Filled.Add, "Floating action button.")
                    }
                }
            },
            snackbarHost = {
                SnackbarHost(
                    hostState = snackbarHostState
                )
            }
        ) { innerPadding ->
            if (localFacts.itemCount == 0) {
                Box(modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                ) {
                    Text(
                        text = "No facts yet",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            } else {
                LazyColumn(
                    state = listState,
                ) {
                    items(localFacts.itemCount, key = { index ->
                        localFacts[index]?.id ?: index
                    }) { index ->
                        localFacts[index]?.let { fact ->
                            ChuckNorrisFactListItem(fact, onItemClick)
                        }
                    }
                    // Add a spacer at the end of the list for FAB
                    item {
                        Spacer(modifier = Modifier.height(80.dp))
                    }
                }
            }
        }
    }

    if (state.value is Success) {
        LaunchedEffect(key1 = Unit) {
            scope.launch {
                // Necessary delay for scroll to work properly
                delay(500)
                listState.animateScrollToItem(0)
            }
        }
    }
}
