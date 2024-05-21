package com.jkjamies.imgur.search.presentation.shared

import android.content.Context
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.content.res.Configuration.UI_MODE_TYPE_NORMAL
import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.decode.VideoFrameDecoder
import com.jkjamies.imgur.api.domain.models.ImgurSearchResult
import com.jkjamies.imgur.search.R
import com.jkjamies.imgur.search.ui.theme.ImgurSearchTheme

@Composable
internal fun ImgurImage(result: ImgurSearchResult) {
    AsyncImage(
        imageLoader = getImageLoader(result.imageType, LocalContext.current),
        modifier = Modifier.fillMaxWidth(),
        model = result.link,
        placeholder = painterResource(R.drawable.ic_launcher_foreground), // TODO: could be better
        contentDescription = result.title,
        contentScale = ContentScale.FillWidth,
    )
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

@Composable
@Preview(name = "Light", backgroundColor = 0xFFFFFFFF, showBackground = true)
@Preview(
    name = "Dark",
    backgroundColor = 0xFF000000,
    showBackground = true,
    uiMode = UI_MODE_NIGHT_YES or UI_MODE_TYPE_NORMAL,
)
private fun ImgurImagePreview() {
    ImgurSearchTheme {
        ImgurImage(
            result =
                ImgurSearchResult(
                    id = "1",
                    title = "Title",
                    link = "https://i.imgur.com/1.jpg",
                    imageType = "image/jpeg",
                ),
        )
    }
}
