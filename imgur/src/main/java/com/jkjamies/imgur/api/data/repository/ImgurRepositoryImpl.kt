package com.jkjamies.imgur.api.data.repository

import co.touchlab.kermit.Logger
import com.jkjamies.imgur.api.SortOption
import com.jkjamies.imgur.api.WindowOption
import com.jkjamies.imgur.api.data.localDataSource.ImgurLocalDataSource
import com.jkjamies.imgur.api.data.remoteDataSource.ImgurRemoteDataSource
import com.jkjamies.imgur.api.domain.models.ImgurSearchResult
import com.jkjamies.imgur.api.domain.models.ImgurSearchResults
import com.jkjamies.imgur.api.domain.repository.ImgurRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Single

@Single(binds = [ImgurRepository::class])
internal class ImgurRepositoryImpl(
    private val localDataSource: ImgurLocalDataSource,
    private val remoteDataSource: ImgurRemoteDataSource,
) : ImgurRepository {
    override suspend fun getImgurSearchResults(
        searchQuery: String,
        sortOption: SortOption?,
        windowOption: WindowOption?,
    ): Flow<Result<ImgurSearchResults?>> =
        flow {
            // emit retained information immediately
            val local = localDataSource.getSearchResults(searchQuery).getOrNull()
            // if all parameters are the same just pull local
            // TODO: lots of room for improvement here, consider using local db for all sort
            //  except top, uses window also - but not a lot of need to keep calling the API
            if (
                local?.searchQuery == searchQuery &&
                local.sortOption.equals(sortOption?.name, true) &&
                local.windowOption.equals(windowOption?.name, true)
            ) {
                emit(Result.success(local))
            } else {
                // TODO: consider always fetch a new set of images to stay updated, even if local data is available
                remoteDataSource.getSearchQueryResults(searchQuery, sortOption, windowOption)
                    .onSuccess { searchResults ->
                        Logger.d("ImgurRepositoryImpl") {
                            """
                            Requested Imgur Search:
                            $searchResults
                            """.trimIndent()
                        }
                        emit(Result.success(searchResults))
                        searchResults?.let {
                            localDataSource.saveSearchResults(
                                searchQuery,
                                sortOption?.name,
                                windowOption?.name,
                                it,
                            )
                        }
                    }
                    .onFailure {
                        if (local == null) { // if we have no local data, emit null
                            emit(Result.failure(it))
                        } else { // otherwise use the local data we have instead
                            Logger.e("ImgurRepositoryImpl") {
                                """
                                Keeping local cached data:
                                Failed to fetch Imgur Search:
                                $it
                                """.trimIndent()
                            }
                        }
                    }
            }
        }

    override suspend fun getImgurImageById(imageId: String): Flow<Result<ImgurSearchResult>> =
        flow {
            localDataSource.getSearchResult(imageId).onSuccess {
                emit(Result.success(it))
            }.onFailure {
                emit(Result.failure(it))
            }
        }
}
