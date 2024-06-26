package com.jkjamies.imgur.api.localDataSource

import android.content.Context
import app.cash.sqldelight.ColumnAdapter
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import co.touchlab.kermit.Logger
import com.jkjamies.imgur.api.ImgurDataList
import com.jkjamies.imgur.api.ImgurDatabase
import com.jkjamies.imgur.api.data.localDataSource.ImgurLocalDataSource
import com.jkjamies.imgur.api.domain.models.ImgurSearchResult
import com.jkjamies.imgur.api.domain.models.ImgurSearchResults
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.koin.core.annotation.Single

@Single(binds = [ImgurLocalDataSource::class])
internal class ImgurLocalDataSourceImpl(
    context: Context,
    private val driver: SqlDriver = AndroidSqliteDriver(ImgurDatabase.Schema, context, "imgur.db"),
) : ImgurLocalDataSource {
    private val listOfSearchResultsAdapter =
        object : ColumnAdapter<List<ImgurSearchResult>, String> {
            override fun decode(databaseValue: String): List<ImgurSearchResult> {
                return if (databaseValue.isEmpty()) {
                    emptyList()
                } else {
                    Json.decodeFromString(databaseValue)
                }
            }

            override fun encode(value: List<ImgurSearchResult>): String {
                return Json.encodeToString(value)
            }
        }

    private val database =
        ImgurDatabase(
            driver = driver,
            ImgurDataListAdapter =
                ImgurDataList.Adapter(
                    imgurSearchResultsAdapter = listOfSearchResultsAdapter,
                ),
        )

    override suspend fun getSearchResults(searchQuery: String): Result<ImgurSearchResults?> {
        // Retrieve the cached data
        val cachedData =
            database.imgurDatabaseQueries.getImgurResultsByQuery(searchQuery).executeAsOneOrNull()
                ?: return Result.failure(Exception("No cached data found for search query: $searchQuery"))

        Logger.d("ImgurLocalDataSourceImpl") {
            """
            Retrieved cached results: $cachedData
            """.trimIndent()
        }

        // Convert the cached data to the domain model
        return Result.success(
            ImgurSearchResults(
                searchQuery = cachedData.searchQuery,
                sortOption = cachedData.sortOption,
                windowOption = cachedData.windowOption,
                imgurResults = cachedData.imgurSearchResults,
            ),
        )
    }

    override suspend fun getSearchResult(id: String): Result<ImgurSearchResult> {
        // Retrieve the cached data
        val cachedData = database.imgurDatabaseQueries.getAllImgurData().executeAsOneOrNull()
        val cachedResult =
            cachedData?.imgurSearchResults?.find { it.id == id } ?: return Result.failure(
                Exception("No cached data found for search result with ID: $id"),
            )

        Logger.d("ImgurLocalDataSourceImpl") {
            """
            Retrieved cached result: $cachedResult
            """.trimIndent()
        }

        // Convert the cached data to the domain model
        return Result.success(
            ImgurSearchResult(
                id = cachedResult.id,
                title = cachedResult.title,
                link = cachedResult.link,
                imageType = cachedResult.imageType,
            ),
        )
    }

    override suspend fun saveSearchResults(
        searchQuery: String,
        sortOption: String?,
        windowOption: String?,
        imgurSearchResults: ImgurSearchResults,
    ) {
        Logger.d("ImgurLocalDataSourceImpl") {
            """
            Saving search results: $imgurSearchResults
            """.trimIndent()
        }

        database.imgurDatabaseQueries.deleteAllData()

        database.imgurDatabaseQueries.insertOrReplaceImgurData(
            searchQuery = searchQuery,
            sortOption = sortOption,
            windowOption = windowOption,
            imgurSearchResults = imgurSearchResults.imgurResults,
        )
    }
}
