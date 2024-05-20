package com.jkjamies.imgur.api.data.repository

import co.touchlab.kermit.Logger
import com.jkjamies.imgur.api.data.localDataSource.ImgurLocalDataSource
import com.jkjamies.imgur.api.data.remoteDataSource.ImgurRemoteDataSource
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
    override suspend fun getImgurSearchResults(searchQuery: String): Flow<ImgurSearchResults?> =
        flow {
            // emit retained information immediately
            val local = localDataSource.getSearchResults(searchQuery)
            if (local != null && local.searchQuery == searchQuery) emit(local)

            // always fetch a new set of images to stay updated, even if local data is available
            remoteDataSource.getSearchQueryResults(searchQuery)
                .onSuccess { searchResults ->
                    Logger.d("ImgurRepositoryImpl") {
                        """
                        Requested Imgur Search:
                        $searchResults
                        """.trimIndent()
                    }
                    emit(searchResults)
                    searchResults?.let { localDataSource.saveSearchResults(searchQuery, it) }
                }
                .onFailure {
                    if (local == null) { // if we have no local data, emit null
                        emit(null)
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
