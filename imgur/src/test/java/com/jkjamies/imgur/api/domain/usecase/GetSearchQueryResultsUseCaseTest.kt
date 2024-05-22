package com.jkjamies.imgur.api.domain.usecase

import com.jkjamies.imgur.api.domain.models.ImgurSearchResult
import com.jkjamies.imgur.api.domain.models.ImgurSearchResults
import com.jkjamies.imgur.api.domain.repository.ImgurRepository
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest

@ExperimentalCoroutinesApi
class GetSearchQueryResultsUseCaseTest : StringSpec({

    val imgurRepository = mockk<ImgurRepository>()
    val getSearchQueryResultsUseCase = GetSearchQueryResultsUseCase(imgurRepository)

    val mockImgurSearchResult =
        ImgurSearchResult(
            id = "123",
            title = "Cute Cat",
            link = "https://imgur.com/cat123",
            imageType = "image/jpeg",
        )

    val mockImgurSearchResults =
        ImgurSearchResults(
            searchQuery = "cats",
            sortOption = "TOP",
            windowOption = "ALL",
            imgurResults = listOf(mockImgurSearchResult),
        )

    "should return search results flow from repository" {
        // Given
        val searchQuery = "cats"
        val expectedResults: Flow<Result<ImgurSearchResults>> =
            flowOf(Result.success(mockImgurSearchResults))

        coEvery { imgurRepository.getImgurSearchResults(searchQuery) } returns expectedResults

        runTest {
            // When
            val results = getSearchQueryResultsUseCase.invoke(searchQuery, null, null)
            // Then
            results shouldBe expectedResults
        }
    }

    "should handle null search results from repository" {
        // Given
        val searchQuery = "dogs"
        val expectedResults: Flow<Result<ImgurSearchResults>> = flowOf(Result.failure(Exception()))
        coEvery { imgurRepository.getImgurSearchResults(searchQuery) } returns expectedResults

        runTest {
            // When
            val results = getSearchQueryResultsUseCase.invoke(searchQuery, null, null)
            // Then
            results shouldBe expectedResults
        }
    }
})
