package com.jkjamies.imgur.search.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.jkjamies.imgur.search.presentation.details.DetailsScreen
import com.jkjamies.imgur.search.presentation.details.DetailsScreenContent
import com.jkjamies.imgur.search.presentation.search.SearchScreen
import com.jkjamies.imgur.search.presentation.search.SearchScreenContent

@Composable
internal fun ImgurSearchNavHost() {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        val navController = rememberNavController()

        NavHost(
            modifier =
                Modifier.fillMaxWidth().padding(innerPadding),
            navController = navController,
            startDestination = SearchScreen,
        ) {
            composable<SearchScreen> {
                SearchScreenContent(
                    onNavigateToDetails = {
                        navController.navigate(
                            DetailsScreen(it),
                        )
                    },
                )
            }

            composable<DetailsScreen> {
                val args = it.toRoute<DetailsScreen>()
                DetailsScreenContent(
                    imageId = args.imageId,
                    onBackClick = {
                        navController.navigateUp()
                    },
                )
            }
        }
    }
}
