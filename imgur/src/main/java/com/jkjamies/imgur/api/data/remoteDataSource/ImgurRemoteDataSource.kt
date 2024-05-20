package com.jkjamies.imgur.api.data.remoteDataSource

import com.jkjamies.imgur.api.domain.models.ImgurSearchResults

/**
 * Remote data source for the Imgur API.
 */
internal interface ImgurRemoteDataSource {
    /**
     * Retrieve the search query results.
     *
     * @param searchQuery The search query to retrieve the results for.
     * @return The search query results for the given [searchQuery].
     */
    suspend fun getSearchQueryResults(searchQuery: String): Result<ImgurSearchResults?>
}
