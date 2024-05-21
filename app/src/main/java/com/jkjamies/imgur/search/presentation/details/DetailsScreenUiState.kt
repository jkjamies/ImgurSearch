package com.jkjamies.imgur.search.presentation.details

import com.jkjamies.imgur.api.domain.models.ImgurSearchResult

internal sealed class DetailsScreenUiState {
    data object Idle : DetailsScreenUiState()

    data class Image(val image: ImgurSearchResult) : DetailsScreenUiState()

    data object Error : DetailsScreenUiState()
}
