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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jkjamies.imgur.api.FilterOptions
import com.jkjamies.imgur.api.SortOption
import com.jkjamies.imgur.api.WindowOption
import com.jkjamies.imgur.search.R
import com.jkjamies.imgur.search.ui.theme.ImgurSearchTheme

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
        Text(text = stringResource(R.string.sort_by))
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            FilterChip(
                selected = filterOptions.sort == SortOption.TIME,
                onClick = {
                    onFilterOptionSelect(filterOptions.copy(sort = SortOption.TIME))
                },
                label = { Text(stringResource(R.string.time)) },
            )
            FilterChip(
                selected = filterOptions.sort == SortOption.VIRAL,
                onClick = {
                    onFilterOptionSelect(filterOptions.copy(sort = SortOption.VIRAL))
                },
                label = { Text(stringResource(R.string.viral)) },
            )
            FilterChip(
                selected = filterOptions.sort == SortOption.TOP,
                onClick = {
                    onFilterOptionSelect(filterOptions.copy(sort = SortOption.TOP))
                },
                label = { Text(stringResource(R.string.top)) },
            )
        }
        AnimatedVisibility(filterOptions.sort == SortOption.TOP) {
            if (filterOptions.window == null) {
                onFilterOptionSelect(filterOptions.copy(window = WindowOption.ALL))
            }
            Column {
                Text(text = stringResource(R.string.window))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    FilterChip(
                        selected = filterOptions.window == WindowOption.DAY,
                        onClick = {
                            onFilterOptionSelect(filterOptions.copy(window = WindowOption.DAY))
                        },
                        label = { Text(stringResource(R.string.day)) },
                    )
                    FilterChip(
                        selected = filterOptions.window == WindowOption.WEEK,
                        onClick = {
                            onFilterOptionSelect(filterOptions.copy(window = WindowOption.WEEK))
                        },
                        label = { Text(stringResource(R.string.week)) },
                    )
                    FilterChip(
                        selected = filterOptions.window == WindowOption.MONTH,
                        onClick = {
                            onFilterOptionSelect(filterOptions.copy(window = WindowOption.MONTH))
                        },
                        label = { Text(stringResource(R.string.month)) },
                    )
                    FilterChip(
                        selected = filterOptions.window == WindowOption.YEAR,
                        onClick = {
                            onFilterOptionSelect(filterOptions.copy(window = WindowOption.YEAR))
                        },
                        label = { Text(stringResource(R.string.year)) },
                    )
                    FilterChip(
                        selected = filterOptions.window == WindowOption.ALL,
                        onClick = {
                            onFilterOptionSelect(filterOptions.copy(window = WindowOption.ALL))
                        },
                        label = { Text(stringResource(R.string.all)) },
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
