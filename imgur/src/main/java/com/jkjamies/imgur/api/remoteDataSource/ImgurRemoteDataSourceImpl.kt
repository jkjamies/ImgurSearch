package com.jkjamies.imgur.api.remoteDataSource

import ImgurErrorResponse
import ImgurResponse
import com.jkjamies.imgur.api.BuildConfig
import com.jkjamies.imgur.api.data.remoteDataSource.ImgurRemoteDataSource
import com.jkjamies.imgur.api.domain.models.ImgurSearchResult
import com.jkjamies.imgur.api.domain.models.ImgurSearchResults
import com.jkjamies.imgur.core.httpClient
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.appendPathSegments
import org.koin.core.annotation.Single

@Single(binds = [ImgurRemoteDataSource::class])
internal class ImgurRemoteDataSourceImpl(
    private val httpClient: HttpClient = httpClient { },
) : ImgurRemoteDataSource {
    override suspend fun getSearchQueryResults(searchQuery: String): Result<ImgurSearchResults?> {
        runCatching {
            // Fetch the Imgur Search Response from the Imgur API
            val request =
                httpClient.get(BuildConfig.imgurBaseUrl) {
                    url {
                        appendPathSegments(BuildConfig.imgurSearchUrl)
                        if (true) { // if given a sort filter
                            appendPathSegments("time") // time, viral, top - defaults to time
                        }
                        if (true) { // if given a time filter
                            appendPathSegments("all") // day, week, month, year, all - defaults to all
                        }
                        parameters.append("q", searchQuery)
                    }
                    headers {
                        append(HttpHeaders.Authorization, "Client-ID b067d5cb828ec5a")
                    }
                }

            // Return the Imgur Search Response
            return when (request.status) {
                HttpStatusCode.OK -> {
                    val results = request.body<ImgurResponse>()
                    val mappedResults =
                        results.data?.flatMap { result ->
                            result.images?.map { image ->
                                ImgurSearchResult(
                                    id = image.id ?: "",
                                    title = result.title ?: "", // Use the title from results.data
                                    link = image.link ?: "",
                                    imageType = image.type ?: "",
                                )
                            } ?: listOf() // result.images is null
                        } ?: listOf() // imgurResponse.data is null
                    Result.success(
                        ImgurSearchResults(
                            searchQuery = searchQuery,
                            imgurResults = mappedResults,
                        ),
                    )
                }

                else -> {
                    val err = request.body<ImgurErrorResponse>()
                    Result.failure(Exception(err.data?.error ?: "Unknown Error"))
                }
            }
        }.onFailure {
            // Return the error if there was one
            return Result.failure(it)
        }
        // This should never be reached
        return Result.failure(Exception("Unknown Error"))
    }
}
