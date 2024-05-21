package com.jkjamies.imgur.search.presentation.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jkjamies.imgur.search.presentation.shared.ImgurImage
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

/**
 * Destination for the image details screen.
 */
@Serializable
internal data class DetailsScreen(val imageId: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun DetailsScreenContent(
    imageId: String,
    onBackClick: () -> Unit,
    viewModel: DetailsScreenViewModel = koinViewModel<DetailsScreenViewModel>(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(imageId) {
        viewModel.fetchImgurById(imageId)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = (uiState as? DetailsScreenUiState.Image)?.image?.title ?: "",
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick,
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                        )
                    }
                },
            )
        },
    ) { innerPadding ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
        ) {
            when (uiState) {
                DetailsScreenUiState.Idle -> {
                    // TODO: show something?
                }

                is DetailsScreenUiState.Image -> {
                    ImgurImage(result = (uiState as DetailsScreenUiState.Image).image)
                }

                DetailsScreenUiState.Error -> {
                    // TODO: show error?
                }
            }
        }
    }
}
