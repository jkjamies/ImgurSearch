package com.jkjamies.imgur.api

import android.content.Context
import co.touchlab.kermit.Logger
import co.touchlab.kermit.koin.KermitKoinLogger
import com.jkjamies.imgur.api.domain.models.ImgurSearchResults
import com.jkjamies.imgur.api.domain.usecase.GetSearchQueryResultsUseCase
import kotlinx.coroutines.flow.Flow
import org.koin.android.ext.koin.androidContext
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.dsl.koinApplication
import org.koin.ksp.generated.module

/**
 * API Facade for the Imgur Search Query Results
 */
class ImgurApi(context: Context) {
    // opting to declare no koin module, takes all from imgur module

    /** Koin application for the Imgur search query results */
    private val koinApp =
        koinApplication {
            logger(KermitKoinLogger(Logger.withTag("Koin Imgur")))
            androidContext(context)
            modules(ImgurModule().module)
        }

    /** Use case for getting Imgur search query results */
    private val getSearchQueryResultsUseCase = koinApp.koin.get<GetSearchQueryResultsUseCase>()

    /**
     * Get the Imgur search query results
     *
     * @param searchQuery the search query to get results for
     * @return the Imgur search query results as a [Flow] of [ImgurSearchResults]
     */
    suspend fun getSearchResults(searchQuery: String): Flow<ImgurSearchResults?> = getSearchQueryResultsUseCase(searchQuery)
}

@Module
@ComponentScan
internal class ImgurModule
