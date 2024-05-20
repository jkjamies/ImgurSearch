package com.jkjamies.imgur.api.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class ImgurSearchResults(
    val searchQuery: String,
    val imgurResults: List<ImgurSearchResult>,
)

@Serializable
data class ImgurSearchResult(
    val id: String,
    val title: String,
    val link: String,
    val imageType: String,
)
