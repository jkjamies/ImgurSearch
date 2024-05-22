package com.jkjamies.imgur.search.presentation.search.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.jkjamies.imgur.api.domain.models.ImgurSearchResult
import com.jkjamies.imgur.search.ui.theme.ImgurSearchTheme

@Composable
internal fun SearchResultsGrid(
    results: List<ImgurSearchResult>,
    onNavigateToDetails: (String) -> Unit,
) {
    LazyVerticalStaggeredGrid(
        modifier = Modifier.padding(vertical = 16.dp),
        columns = StaggeredGridCells.Adaptive(minSize = 128.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalItemSpacing = 16.dp,
        content = {
            items(results) { result ->
                SearchResultCard(
                    result = result,
                    onClick = { onNavigateToDetails(result.id) },
                )
            }
        },
    )
}

@Composable
@PreviewLightDark
private fun SearchResultsGridPreview() {
    ImgurSearchTheme {
        SearchResultsGrid(
            results =
                listOf(
                    ImgurSearchResult(
                        id = "1",
                        title = "Title",
                        link = "https://i.imgur.com/abc123.jpg",
                        imageType = "image/jpeg",
                    ),
                    ImgurSearchResult(
                        id = "2",
                        title = "Title",
                        link = "https://i.imgur.com/abc123.jpg",
                        imageType = "image/jpeg",
                    ),
                    ImgurSearchResult(
                        id = "3",
                        title = "Title",
                        link = "https://i.imgur.com/abc123.jpg",
                        imageType = "image/jpeg",
                    ),
                    ImgurSearchResult(
                        id = "4",
                        title = "Title",
                        link = "https://i.imgur.com/abc123.jpg",
                        imageType = "image/jpeg",
                    ),
                ),
            onNavigateToDetails = { },
        )
    }
}
