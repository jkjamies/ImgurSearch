package com.jkjamies.imgur.search.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.jkjamies.imgur.search.R

@Composable
internal fun SearchScreenIdle() {
    val idleStateAnimation by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.idle))

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
