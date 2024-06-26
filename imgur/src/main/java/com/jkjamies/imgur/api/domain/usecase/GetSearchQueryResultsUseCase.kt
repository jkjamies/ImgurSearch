package com.jkjamies.imgur.api.domain.usecase

import com.jkjamies.imgur.api.SortOption
import com.jkjamies.imgur.api.WindowOption
import com.jkjamies.imgur.api.domain.models.ImgurSearchResults
import com.jkjamies.imgur.api.domain.repository.ImgurRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Single

/**
 * Use case for getting Imgur search query results
 */
@Single
internal class GetSearchQueryResultsUseCase(
    private val imgurRepository: ImgurRepository,
) {
    /**
     * Get Imgur search query results
     *
     * @param searchQuery the search query to get results for
     * @param sortOption the sort option for the search results
     * @param windowOption the window option for the search results
     * @return the Imgur search query results as a [Flow] of [ImgurSearchResults]
     */
    suspend operator fun invoke(
        searchQuery: String,
        sortOption: SortOption?,
        windowOption: WindowOption?,
    ): Flow<Result<ImgurSearchResults?>> = imgurRepository.getImgurSearchResults(searchQuery, sortOption, windowOption)
}
