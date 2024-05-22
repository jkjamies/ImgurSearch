package com.jkjamies.imgur.search.presentation.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jkjamies.imgur.api.ImgurApi
import com.jkjamies.imgur.api.SortOption
import com.jkjamies.imgur.api.WindowOption
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.updateAndGet
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class SearchScreenViewModel(
    private val imgurApi: ImgurApi,
) : ViewModel() {
    var uiState: MutableStateFlow<SearchScreenUiState> = MutableStateFlow(SearchScreenUiState.Idle)
        private set

    /**
     * Search for images on Imgur
     *
     * @param searchQuery the query to search for
     */
    fun search(
        searchQuery: String,
        sortOption: SortOption?,
        windowOption: WindowOption?,
    ) {
        viewModelScope.launch {
            uiState.updateAndGet { SearchScreenUiState.Loading }
            // TODO: sometimes it is too quick, just for looks - remove good option?
            delay(2000)
            imgurApi.getSearchResults(searchQuery, sortOption, windowOption)
                .collect { imgurResponse ->
                    Log.e("TAG", "imgurResponse: $imgurResponse")
                    imgurResponse.onSuccess { imgurs ->
                        uiState.updateAndGet {
                            if (imgurs?.imgurResults.isNullOrEmpty()) {
                                uiState.updateAndGet { SearchScreenUiState.Error }
                            } else {
                                SearchScreenUiState.Results(
                                    results = imgurs?.imgurResults ?: emptyList(),
                                )
                            }
                        }
                    }.onFailure {
                        uiState.updateAndGet { SearchScreenUiState.Error }
                    }
                }
        }
    }
}
