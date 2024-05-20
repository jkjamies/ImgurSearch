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
            emit(local)

            // if missing or different search query, fetch from remote and save to local, emit remote data
            if (local == null || local.searchQuery != searchQuery) {
                remoteDataSource.getSearchQueryResults(searchQuery)
                    .onSuccess { searchResults ->
                        Logger.d("ApodRemoteDataSourceImpl") {
                            """
                            Requested Astronomy Picture of the Day:
                            $searchResults
                            """.trimIndent()
                        }
                        emit(searchResults)
                        searchResults?.let { localDataSource.saveSearchResults(searchQuery, it) }
                    }
                    .onFailure {
                        emit(null)
                    }
            }
        }
}
