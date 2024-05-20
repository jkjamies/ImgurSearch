package com.jkjamies.imgur.search.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.jkjamies.imgur.search.FilterOptions
import com.jkjamies.imgur.search.R
import com.jkjamies.imgur.search.presentation.components.SearchAppBar
import com.jkjamies.imgur.search.presentation.components.SearchResultsGrid
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun SearchScreenContent(
    onNavigateToDetails: (String) -> Unit,
    viewModel: SearchScreenViewModel = koinViewModel<SearchScreenViewModel>(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val idleStateAnimation by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.idle))
    val searchingStateAnimation by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.searching))
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
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                        ) {
                            Text(
                                text = "Search for images",
                                style = MaterialTheme.typography.headlineLarge,
                            )
                            LottieAnimation(
                                modifier = Modifier.testTag("idleLottie"),
                                composition = idleStateAnimation,
                            )
                        }
                    }

                    SearchScreenUiState.Loading -> {
                        Column(
                            modifier =
                                Modifier
                                    .fillMaxSize()
                                    .padding(paddingValues = padding),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Text(
                                modifier = Modifier.padding(horizontal = 16.dp),
                                text = "Searching images for\n${lastKnownSearchExecuted.value}...",
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.headlineLarge,
                            )
                            LottieAnimation(
                                modifier = Modifier.testTag("searchingLottie"),
                                composition = searchingStateAnimation,
                                iterations = LottieConstants.IterateForever,
                            )
                        }
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
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                        ) {
                            Icon(
                                imageVector = Icons.Default.Error,
                                contentDescription = "Error",
                                modifier = Modifier.size(48.dp),
                            )
                        }
                    }
                }
            }
        },
    )
}
