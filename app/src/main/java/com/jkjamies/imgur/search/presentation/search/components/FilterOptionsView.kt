package com.jkjamies.imgur.search.presentation.search.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.content.res.Configuration.UI_MODE_TYPE_NORMAL
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jkjamies.imgur.search.ui.theme.ImgurSearchTheme
import kotlinx.serialization.Serializable

/**
 * Filter options for the search results.
 */
@Serializable
internal data class FilterOptions(
    val sort: SortOption = SortOption.TIME,
    val window: WindowOption? = null,
)

/**
 * Sort options for the search results.
 */
internal enum class SortOption { TIME, VIRAL, TOP }

/**
 * Window options for the search results.
 */
internal enum class WindowOption { DAY, WEEK, MONTH, YEAR, ALL }

@Composable
internal fun FilterOptionsView(
    filterOptions: FilterOptions,
    onFilterOptionSelect: (FilterOptions) -> Unit,
) {
    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(8.dp),
    ) {
        Text(text = "Sort by")
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            FilterChip(
                selected = filterOptions.sort == SortOption.TIME,
                onClick = {
                    onFilterOptionSelect(filterOptions.copy(sort = SortOption.TIME))
                },
                label = { Text("Time") },
            )
            FilterChip(
                selected = filterOptions.sort == SortOption.VIRAL,
                onClick = {
                    onFilterOptionSelect(filterOptions.copy(sort = SortOption.VIRAL))
                },
                label = { Text("Viral") },
            )
            FilterChip(
                selected = filterOptions.sort == SortOption.TOP,
                onClick = {
                    onFilterOptionSelect(filterOptions.copy(sort = SortOption.TOP))
                },
                label = { Text("Top") },
            )
        }
        AnimatedVisibility(filterOptions.sort == SortOption.TOP) {
            if (filterOptions.window == null) {
                onFilterOptionSelect(filterOptions.copy(window = WindowOption.ALL))
            }
            Column {
                Text(text = "Window")
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    FilterChip(
                        selected = filterOptions.window == WindowOption.DAY,
                        onClick = {
                            onFilterOptionSelect(filterOptions.copy(window = WindowOption.DAY))
                        },
                        label = { Text("Day") },
                    )
                    FilterChip(
                        selected = filterOptions.window == WindowOption.WEEK,
                        onClick = {
                            onFilterOptionSelect(filterOptions.copy(window = WindowOption.WEEK))
                        },
                        label = { Text("Week") },
                    )
                    FilterChip(
                        selected = filterOptions.window == WindowOption.MONTH,
                        onClick = {
                            onFilterOptionSelect(filterOptions.copy(window = WindowOption.MONTH))
                        },
                        label = { Text("Month") },
                    )
                    FilterChip(
                        selected = filterOptions.window == WindowOption.YEAR,
                        onClick = {
                            onFilterOptionSelect(filterOptions.copy(window = WindowOption.YEAR))
                        },
                        label = { Text("Year") },
                    )
                    FilterChip(
                        selected = filterOptions.window == WindowOption.ALL,
                        onClick = {
                            onFilterOptionSelect(filterOptions.copy(window = WindowOption.ALL))
                        },
                        label = { Text("All") },
                    )
                }
            }
        }
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
private fun FilterOptionsViewTimePreview() {
    ImgurSearchTheme {
        FilterOptionsView(
            filterOptions =
                FilterOptions(
                    sort = SortOption.TIME,
                    window = WindowOption.ALL,
                ),
        ) { }
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
private fun FilterOptionsViewTopPreview() {
    ImgurSearchTheme {
        FilterOptionsView(
            filterOptions =
                FilterOptions(
                    sort = SortOption.TOP,
                    window = WindowOption.ALL,
                ),
        ) { }
    }
}
