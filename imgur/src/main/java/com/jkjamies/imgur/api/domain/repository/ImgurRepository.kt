package com.jkjamies.imgur.api.domain.repository

import com.jkjamies.imgur.api.SortOption
import com.jkjamies.imgur.api.WindowOption
import com.jkjamies.imgur.api.domain.models.ImgurSearchResult
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
     * @param sortOption The sort option for the search results.
     * @param windowOption The window option for the search results.
     * @return The search results for the given [searchQuery].
     */
    suspend fun getImgurSearchResults(
        searchQuery: String,
        sortOption: SortOption? = null,
        windowOption: WindowOption? = null,
    ): Flow<Result<ImgurSearchResults?>>

    /**
     * Retrieve the image with the given [imageId].
     *
     * @param imageId The ID of the image to retrieve.
     * @return The image with the given [imageId].
     */
    suspend fun getImgurImageById(imageId: String): Flow<Result<ImgurSearchResult>>
}
