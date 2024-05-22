package com.jkjamies.imgur.api.data.repository

import com.jkjamies.imgur.api.SortOption
import com.jkjamies.imgur.api.WindowOption
import com.jkjamies.imgur.api.data.localDataSource.ImgurLocalDataSource
import com.jkjamies.imgur.api.data.remoteDataSource.ImgurRemoteDataSource
import com.jkjamies.imgur.api.domain.models.ImgurSearchResult
import com.jkjamies.imgur.api.domain.models.ImgurSearchResults
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest

@ExperimentalCoroutinesApi
class ImgurRepositoryImplTest : StringSpec({

    val localDataSource = mockk<ImgurLocalDataSource>()
    val remoteDataSource = mockk<ImgurRemoteDataSource>()
    val repository = ImgurRepositoryImpl(localDataSource, remoteDataSource)

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
            sortOption = SortOption.TOP.name,
            windowOption = WindowOption.ALL.name,
            imgurResults = listOf(mockImgurSearchResult),
        )

    val mockNonMatchingSearchResults =
        ImgurSearchResults(
            searchQuery = "dogs",
            sortOption = SortOption.TOP.name,
            windowOption = WindowOption.ALL.name,
            imgurResults = listOf(mockImgurSearchResult),
        )

    "should fetch and save remote data if local data is null" {
        // Given
        val searchQuery = "dogs"
        coEvery { localDataSource.getSearchResults(searchQuery) } returns Result.failure(Exception("No results"))
        coEvery { remoteDataSource.getSearchQueryResults(searchQuery, any(), any()) } returns
            Result.success(
                mockImgurSearchResults,
            )
        coEvery {
            localDataSource.saveSearchResults(
                searchQuery,
                any(),
                any(),
                mockImgurSearchResults,
            )
        } just Runs

        runTest {
            // When
            val results =
                repository.getImgurSearchResults(searchQuery, SortOption.TOP, WindowOption.ALL)
            val emittedResults = mutableListOf<ImgurSearchResults?>()
            results.collect { emittedResults.add(it.getOrNull()) }

            // Then
            emittedResults.size shouldBe 1
            emittedResults[0] shouldBe mockImgurSearchResults

            coVerify {
                localDataSource.saveSearchResults(
                    searchQuery,
                    SortOption.TOP.name,
                    WindowOption.ALL.name,
                    mockImgurSearchResults,
                )
            }
        }
    }

    "should emit null if both local and remote data are unavailable" {
        // Given
        val searchQuery = "birds"
        coEvery { localDataSource.getSearchResults(searchQuery) } returns Result.failure(Exception("No results"))
        coEvery { remoteDataSource.getSearchQueryResults(searchQuery, any(), any()) } returns
            Result.failure(
                Exception("No results"),
            )

        runTest {
            // When
            val results = repository.getImgurSearchResults(searchQuery)
            val emittedResults = mutableListOf<ImgurSearchResults?>()
            results.collect { emittedResults.add(it.getOrNull()) }

            // Then
            emittedResults.size shouldBe 1
            emittedResults[0] shouldBe null
        }
    }

    "should keep and emit local data if remote data fetch fails" {
        // Given
        val searchQuery = "cats"
        coEvery { localDataSource.getSearchResults(searchQuery) } returns
            Result.success(
                mockImgurSearchResults,
            )
        coEvery { remoteDataSource.getSearchQueryResults(searchQuery, any(), any()) } returns
            Result.failure(
                Exception("No results"),
            )

        runTest {
            // When
            val results =
                repository.getImgurSearchResults(searchQuery, SortOption.TOP, WindowOption.ALL)
            val emittedResults = mutableListOf<ImgurSearchResults?>()
            results.collect { emittedResults.add(it.getOrNull()) }

            // Then
            emittedResults.size shouldBe 1
            emittedResults[0] shouldBe mockImgurSearchResults
        }
    }

    "should fetch remote data if local data has non-matching search query" {
        // Given
        val searchQuery = "cats"
        coEvery { localDataSource.getSearchResults(searchQuery) } returns
            Result.success(
                mockNonMatchingSearchResults,
            )
        coEvery { remoteDataSource.getSearchQueryResults(searchQuery, any(), any()) } returns
            Result.success(
                mockImgurSearchResults,
            )
        coEvery {
            localDataSource.saveSearchResults(
                searchQuery,
                any(),
                any(),
                mockImgurSearchResults,
            )
        } just Runs

        runTest {
            // When
            val results = repository.getImgurSearchResults(searchQuery)
            val emittedResults = mutableListOf<ImgurSearchResults?>()
            results.collect { emittedResults.add(it.getOrNull()) }

            // Then
            emittedResults.size shouldBe 1
            emittedResults[0] shouldBe mockImgurSearchResults

            coVerify {
                localDataSource.saveSearchResults(
                    searchQuery,
                    null,
                    null,
                    mockImgurSearchResults,
                )
            }
        }
    }

    "should emit ImgurSearchResult for valid imageId" {
        // Given
        val imageId = "123"
        coEvery { localDataSource.getSearchResult(imageId) } returns
            Result.success(
                mockImgurSearchResult,
            )

        runTest {
            // When
            val resultFlow = repository.getImgurImageById(imageId)
            val resultList = mutableListOf<ImgurSearchResult?>()
            resultFlow.collect { resultList.add(it.getOrNull()) }

            // Then
            resultList.size shouldBe 1
            resultList[0] shouldBe mockImgurSearchResult
        }
    }

    "should emit null for invalid imageId" {
        // Given
        val imageId = "invalidId"
        coEvery { localDataSource.getSearchResult(imageId) } returns Result.failure(Exception("No result found"))

        runTest {
            // When
            val resultFlow = repository.getImgurImageById(imageId)
            val resultList = mutableListOf<ImgurSearchResult?>()
            resultFlow.collect { resultList.add(it.getOrNull()) }

            // Then
            resultList.size shouldBe 1
            resultList[0] shouldBe null
        }
    }
})
