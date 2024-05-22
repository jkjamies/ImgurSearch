package com.jkjamies.imgur.api

import kotlinx.serialization.Serializable

/**
 * Filter options for the search results.
 */
@Serializable
data class FilterOptions(
    val sort: SortOption = SortOption.TIME,
    val window: WindowOption? = null,
)

/**
 * Sort options for the search results.
 */
enum class SortOption { TIME, VIRAL, TOP }

/**
 * Window options for the search results.
 */
enum class WindowOption { DAY, WEEK, MONTH, YEAR, ALL }
