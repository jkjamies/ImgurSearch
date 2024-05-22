package com.jkjamies.imgur.api.data.localDataSource

import com.jkjamies.imgur.api.domain.models.ImgurSearchResult
import com.jkjamies.imgur.api.domain.models.ImgurSearchResults

/**
 * Local data source for the Imgur API.
 */
internal interface ImgurLocalDataSource {
    /**
     * Retrieve the cached search results.
     *
     * @return The cached search results, or null if there are no cached results.
     */
    suspend fun getSearchResults(searchQuery: String): Result<ImgurSearchResults?>

    /**
     * Retrieve the cached search result with the given [id].
     *
     * @param id The ID of the search result to retrieve.
     * @return The cached search result with the given [id], or null if there is no cached result with that ID.
     */
    suspend fun getSearchResult(id: String): Result<ImgurSearchResult>

    /**
     * Save the given [imgurResponse] as the search results.
     *
     * @param searchQuery The search query to save the results for.
     * @param sortOption The sort option for the search results.
     * @param windowOption The window option for the search results.
     * @param imgurSearchResults The search results to save.
     */
    suspend fun saveSearchResults(
        searchQuery: String,
        sortOption: String?,
        windowOption: String?,
        imgurSearchResults: ImgurSearchResults,
    )
}
