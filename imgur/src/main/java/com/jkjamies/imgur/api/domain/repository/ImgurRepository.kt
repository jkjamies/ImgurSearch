package com.jkjamies.imgur.api.domain.repository

import com.jkjamies.imgur.api.domain.models.ImgurSearchResults
import kotlinx.coroutines.flow.Flow

/**
 * Repository for the Imgur API.
 */
internal interface ImgurRepository {
    /**
     * Retrieve the search results for the given [searchQuery].
     *
     * @param searchQuery The search query to retrieve the results for.
     * @return The search results for the given [searchQuery].
     */
    suspend fun getImgurSearchResults(searchQuery: String): Flow<ImgurSearchResults?>
}
