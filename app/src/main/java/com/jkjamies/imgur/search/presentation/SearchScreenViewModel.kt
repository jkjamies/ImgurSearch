package com.jkjamies.imgur.search.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jkjamies.imgur.api.ImgurApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.updateAndGet
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class SearchScreenViewModel(
    private val imgurApi: ImgurApi,
) : ViewModel() {
    var uiState: MutableStateFlow<SearchScreenUiState> = MutableStateFlow(SearchScreenUiState.Idle)
        private set

    fun search(searchQuery: String) {
        viewModelScope.launch {
            uiState.updateAndGet { SearchScreenUiState.Loading }
            // TODO: sometimes it is too quick, just for looks - remove good option?
            delay(2000)
            imgurApi.getSearchResults(searchQuery).collect { imgurResponse ->
                Log.e("TAG", "imgurResponse: $imgurResponse")
                uiState.updateAndGet {
                    if (imgurResponse == null || imgurResponse.imgurResults.isEmpty()) {
                        SearchScreenUiState.Error
                    } else {
                        SearchScreenUiState.Results(
                            results = imgurResponse.imgurResults,
                        )
                    }
                }
            }
        }
    }

    fun getPage() {
        // Make API call to get the next page of results
        // ...

        // Update the search results list
        uiState.updateAndGet { SearchScreenUiState.Loading }
    }
}
