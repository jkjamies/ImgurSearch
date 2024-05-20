package com.jkjamies.imgur.search.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jkjamies.imgur.search.FilterOptions
import com.jkjamies.imgur.search.presentation.components.SearchAppBar
import com.jkjamies.imgur.search.presentation.components.SearchResultsGrid
import com.jkjamies.imgur.search.presentation.components.SearchScreenError
import com.jkjamies.imgur.search.presentation.components.SearchScreenIdle
import com.jkjamies.imgur.search.presentation.components.SearchScreenLoading
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun SearchScreenContent(
    onNavigateToDetails: (String) -> Unit,
    viewModel: SearchScreenViewModel = koinViewModel<SearchScreenViewModel>(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val lastKnownSearchExecuted = rememberSaveable { mutableStateOf("") }
    val filterOptions = remember { mutableStateOf(FilterOptions()) }

    Scaffold(
        topBar = {
            SearchAppBar(
                onExecuteSearch = {
                    lastKnownSearchExecuted.value = it
                    viewModel.search(lastKnownSearchExecuted.value)
                },
                onFilterOptionsClicked = { /* Show filter options dialog */ },
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                when (uiState) {
                    SearchScreenUiState.Idle -> {
                        SearchScreenIdle()
                    }

                    SearchScreenUiState.Loading -> {
                        SearchScreenLoading(
                            padding = padding,
                            searchQuery = lastKnownSearchExecuted.value,
                        )
                    }

                    is SearchScreenUiState.Results -> {
                        Column(modifier = Modifier.padding(paddingValues = padding)) {
                            // Search results grid
                            SearchResultsGrid(
                                results = (uiState as SearchScreenUiState.Results).results,
                                filterOptions = filterOptions.value,
                                onNavigateToDetails = onNavigateToDetails,
                            )

                            // TODO: only if more than one page and only if results currently exist
                            // Load more button
                            Button(
                                onClick = { /*page update*/ },
                                modifier = Modifier.align(Alignment.CenterHorizontally),
                            ) {
                                Text("Load More")
                            }
                        }
                    }

                    is SearchScreenUiState.Error -> {
                        SearchScreenError()
                    }
                }
            }
        },
    )
}
