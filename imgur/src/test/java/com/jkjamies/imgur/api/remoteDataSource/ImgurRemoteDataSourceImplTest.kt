package com.jkjamies.imgur.api.remoteDataSource

import com.jkjamies.imgur.api.domain.models.ImgurSearchResult
import com.jkjamies.imgur.api.domain.models.ImgurSearchResults
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import io.ktor.utils.io.ByteReadChannel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json

@ExperimentalCoroutinesApi
class ImgurRemoteDataSourceImplTest : StringSpec({

    "should return Imgur search results on successful HTTP request" {
        val mockEngine =
            MockEngine { request ->
                respond(
                    content = ByteReadChannel(remoteDataSourceMockResponse),
                    status = HttpStatusCode.OK,
                    headers = headersOf(HttpHeaders.ContentType, "application/json"),
                )
            }

        val httpClient =
            HttpClient(mockEngine) {
                install(ContentNegotiation) {
                    json(
                        Json {
                            explicitNulls = false
                            ignoreUnknownKeys = true
                        },
                    )
                }
            }

        val remoteDataSource = ImgurRemoteDataSourceImpl(httpClient)

        val expectedSearchResults =
            ImgurSearchResults(
                searchQuery = "cats",
                imgurResults =
                    listOf(
                        ImgurSearchResult(
                            id = "PUOfrQW",
                            title = "cute cat",
                            link = "https://i.imgur.com/PUOfrQW.jpg",
                            imageType = "image/jpeg",
                        ),
                    ),
            )

        runTest {
            remoteDataSource.getSearchQueryResults("cats") shouldBe
                Result.success(
                    expectedSearchResults,
                )
        }
    }

    "should return failure on unsuccessful HTTP request" {
        val mockEngineWithError =
            MockEngine { request ->
                respond(
                    content = ByteReadChannel(remoteDataSourceMockResponse),
                    status = HttpStatusCode.InternalServerError,
                    headers = headersOf(HttpHeaders.ContentType, "application/json"),
                )
            }

        val httpClientWithError =
            HttpClient(mockEngineWithError) {
                install(ContentNegotiation) {
                    json()
                }
            }

        val remoteDataSource = ImgurRemoteDataSourceImpl(httpClientWithError)

        runTest {
            val actualResults = remoteDataSource.getSearchQueryResults("cats")
            actualResults.isFailure shouldBe true
        }
    }
})
