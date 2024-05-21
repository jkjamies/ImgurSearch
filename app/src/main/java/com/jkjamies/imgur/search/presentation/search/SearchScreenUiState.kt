package com.jkjamies.imgur.search.presentation.search

import com.jkjamies.imgur.api.domain.models.ImgurSearchResult

internal sealed class SearchScreenUiState {
    data object Idle : SearchScreenUiState()

    data object Loading : SearchScreenUiState()

    data class Results(val results: List<ImgurSearchResult>) : SearchScreenUiState()

    data object Error : SearchScreenUiState()
}
