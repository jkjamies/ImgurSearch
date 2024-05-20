package com.jkjamies.imgur.search.presentation.components

import android.content.Context
import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.decode.VideoFrameDecoder
import com.jkjamies.imgur.api.domain.models.ImgurSearchResult
import com.jkjamies.imgur.search.R

@Composable
fun SearchResultCard(
    result: ImgurSearchResult,
    onClick: () -> Unit,
) {
    Card(
        modifier =
            Modifier
                .wrapContentSize()
                .defaultMinSize(minHeight = 48.dp)
                .clickable(onClick = onClick),
        shape = RoundedCornerShape(8.dp),
    ) {
        AsyncImage(
            imageLoader = getImageLoader(result.imageType, LocalContext.current),
            modifier = Modifier.fillMaxWidth(),
            model = result.link,
            placeholder = painterResource(R.drawable.ic_launcher_foreground), // TODO: could be better
            contentDescription = result.title,
            contentScale = ContentScale.FillWidth,
        )
    }
}

@Composable
private fun getImageLoader(
    imageType: String,
    context: Context,
) = when (imageType) {
    "image/gif" -> {
        ImageLoader.Builder(context)
            .components {
                // Android P added support for decoding GIFs natively.
                if (SDK_INT >= 28) {
                    add(ImageDecoderDecoder.Factory())
                } else {
                    add(GifDecoder.Factory())
                }
            }
            .crossfade(true)
            .build()
    }

    "video/mp4" -> {
        ImageLoader.Builder(context = LocalContext.current)
            .components {
                add(VideoFrameDecoder.Factory())
            }
            .crossfade(true)
            .build()
    }

    else -> { // covers image/jpeg and image/png
        ImageLoader.Builder(context = LocalContext.current)
            .crossfade(true)
            .build()
    }
}
