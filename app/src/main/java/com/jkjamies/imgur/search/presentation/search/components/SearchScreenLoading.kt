package com.jkjamies.imgur.search.presentation.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.content.res.Configuration.UI_MODE_TYPE_NORMAL
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.jkjamies.imgur.search.R
import com.jkjamies.imgur.search.ui.theme.ImgurSearchTheme

@Composable
internal fun SearchScreenLoading(
    padding: PaddingValues,
    searchQuery: String,
) {
    val searchingStateAnimation by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.searching))

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
            text = "Searching images for\n$searchQuery...",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground,
        )
        LottieAnimation(
            modifier = Modifier.testTag("searchingLottie"),
            composition = searchingStateAnimation,
            iterations = LottieConstants.IterateForever,
        )
    }
}

@Composable
@Preview(name = "Light", backgroundColor = 0xFFFFFFFF, showBackground = true)
@Preview(
    name = "Dark",
    backgroundColor = 0xFF000000,
    showBackground = true,
    uiMode = UI_MODE_NIGHT_YES or UI_MODE_TYPE_NORMAL,
)
private fun SearchScreenLoadingPreview() {
    ImgurSearchTheme {
        SearchScreenLoading(
            padding = PaddingValues(16.dp),
            searchQuery = "cats",
        )
    }
}
