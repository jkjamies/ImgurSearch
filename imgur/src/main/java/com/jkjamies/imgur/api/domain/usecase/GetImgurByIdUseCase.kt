package com.jkjamies.imgur.api.domain.usecase

import com.jkjamies.imgur.api.domain.models.ImgurSearchResult
import com.jkjamies.imgur.api.domain.repository.ImgurRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Single

/**
 * Use case for getting Imgur image by ID
 */
@Single
internal class GetImgurByIdUseCase(
    private val imgurRepository: ImgurRepository,
) {
    /**
     * Get the Imgur image by ID
     *
     * @param imageId the ID of the image to get
     * @return the Imgur image by ID as a [Flow] of [ImgurSearchResult]
     */
    suspend operator fun invoke(imageId: String): Flow<ImgurSearchResult?> = imgurRepository.getImgurImageById(imageId)
}
