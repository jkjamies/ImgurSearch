package com.jkjamies.imgur.search

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.jkjamies.imgur.search.presentation.SearchScreenContent
import com.jkjamies.imgur.search.ui.theme.ImgurSearchTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ImgurSearchTheme {
                SearchScreenContent(
                    onNavigateToDetails = { /* Navigate to details screen */ },
                )
            }
        }
    }
}

data class FilterOptions(val categories: List<String> = emptyList())
