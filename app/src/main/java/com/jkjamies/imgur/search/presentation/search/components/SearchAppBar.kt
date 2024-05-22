package com.jkjamies.imgur.search.presentation.search.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.jkjamies.imgur.search.R
import com.jkjamies.imgur.search.ui.theme.ImgurSearchTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SearchAppBar(
    onExecuteSearch: (String) -> Unit,
    onFilterOptionsClicked: () -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val searchQuery = rememberSaveable { mutableStateOf("") }
    // Search
    TopAppBar(
        title = {
            TextField(
                value = searchQuery.value,
                onValueChange = { searchQuery.value = it },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = stringResource(R.string.filter_options),
                    )
                },
                trailingIcon = {
                    IconButton(onClick = {
                        keyboardController?.hide()
                        focusManager.clearFocus()
                        onExecuteSearch(searchQuery.value)
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Send,
                            contentDescription = stringResource(R.string.filter_options),
                        )
                    }
                },
                shape = RoundedCornerShape(24.dp),
                placeholder = { Text(stringResource(R.string.search_for_images)) },
                colors =
                    TextFieldDefaults.colors(
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                    ),
                maxLines = 1,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions =
                    KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                            focusManager.clearFocus()
                            onExecuteSearch(searchQuery.value)
                        },
                    ),
            )
        },
        actions = {
            IconButton(
                modifier = Modifier.padding(horizontal = 16.dp),
                onClick = onFilterOptionsClicked,
            ) {
                Icon(
                    imageVector = Icons.Filled.FilterList,
                    contentDescription = stringResource(R.string.filter_results),
                )
            }
        },
    )
}

@Composable
@PreviewLightDark
private fun SearchAppBarPreview() {
    ImgurSearchTheme {
        SearchAppBar(onExecuteSearch = { }, onFilterOptionsClicked = { })
    }
}
