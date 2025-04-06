package lv.chernishenko.chucknorrisfacts.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import lv.chernishenko.chucknorrisfacts.ui.listitem.ChuckNorrisFactListItem
import lv.chernishenko.chucknorrisfacts.ui.theme.ChuckNorrisFactsTheme
import lv.chernishenko.chucknorrisfacts.viewmodel.ChuckNorrisViewModel
import lv.chernishenko.chucknorrisfacts.viewmodel.MainScreenState.Loading

@Composable
fun StartScreen(
    viewModel: ChuckNorrisViewModel = hiltViewModel()
) {
    val localFacts = viewModel.pager.collectAsLazyPagingItems()
    val state = viewModel.uiState.collectAsState()

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
        ) { innerPadding ->
            if (localFacts.itemCount == 0) {
                Box(modifier = Modifier.padding(innerPadding)) {
                    Text(
                        text = "No facts yet",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            } else {
                LazyColumn {
                    items(localFacts.itemCount, key = { index ->
                        localFacts[index]?.id ?: index
                    }) { index ->
                        localFacts[index]?.let { fact ->
                            ChuckNorrisFactListItem(fact)
                        }
                    }
                    item {
                        Spacer(modifier = Modifier.height(78.dp))
                    }
                }
            }
        }
    }
}
