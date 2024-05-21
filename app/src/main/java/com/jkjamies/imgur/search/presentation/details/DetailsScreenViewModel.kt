package com.jkjamies.imgur.search.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jkjamies.imgur.api.ImgurApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.updateAndGet
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class DetailsScreenViewModel(
    private val imgurApi: ImgurApi,
) : ViewModel() {
    var uiState: MutableStateFlow<DetailsScreenUiState> =
        MutableStateFlow(DetailsScreenUiState.Idle)
        private set

    /**
     * Fetch the Imgur image by ID
     *
     * @param imageId the ID of the image to fetch
     */
    fun fetchImgurById(imageId: String) {
        viewModelScope.launch {
            imgurApi.getImageById(imageId).collect { imgurResult ->
                uiState.updateAndGet {
                    if (imgurResult == null) {
                        DetailsScreenUiState.Error
                    } else {
                        DetailsScreenUiState.Image(
                            image = imgurResult,
                        )
                    }
                }
            }
        }
    }
}
