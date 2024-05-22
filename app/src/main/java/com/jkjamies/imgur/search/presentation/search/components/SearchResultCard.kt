package com.jkjamies.imgur.search.presentation.search.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.jkjamies.imgur.api.domain.models.ImgurSearchResult
import com.jkjamies.imgur.search.presentation.shared.ImgurImage
import com.jkjamies.imgur.search.ui.theme.ImgurSearchTheme

@Composable
internal fun SearchResultCard(
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
        ImgurImage(result = result)
    }
}

@Composable
@PreviewLightDark
private fun SearchResultCardPreview() {
    ImgurSearchTheme {
        SearchResultCard(
            result =
                ImgurSearchResult(
                    id = "1",
                    title = "Title",
                    link = "https://i.imgur.com/1.jpg",
                    imageType = "image/jpeg",
                ),
            onClick = { },
        )
    }
}
