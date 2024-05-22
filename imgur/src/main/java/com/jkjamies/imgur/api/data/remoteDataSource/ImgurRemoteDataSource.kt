package com.jkjamies.imgur.api.data.remoteDataSource

import com.jkjamies.imgur.api.SortOption
import com.jkjamies.imgur.api.WindowOption
import com.jkjamies.imgur.api.domain.models.ImgurSearchResults

/**
 * Remote data source for the Imgur API.
 */
internal interface ImgurRemoteDataSource {
    /**
     * Retrieve the search query results.
     *
     * @param searchQuery The search query to retrieve the results for.
     * @param sortOption The sort option for the search results.
     * @param windowOption The window option for the search results.
     * @return The search query results for the given [searchQuery].
     */
    suspend fun getSearchQueryResults(
        searchQuery: String,
        sortOption: SortOption?,
        windowOption: WindowOption?,
    ): Result<ImgurSearchResults?>
}
