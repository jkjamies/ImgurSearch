package com.jkjamies.imgur.search.presentation

import com.jkjamies.imgur.api.domain.models.ImgurSearchResult

sealed class SearchScreenUiState {
    data object Idle : SearchScreenUiState()

    data object Loading : SearchScreenUiState()

    data class Results(val results: List<ImgurSearchResult>) : SearchScreenUiState()

    data object Error : SearchScreenUiState()
}
