import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

/**
 * Response from the Imgur API
 */
@Serializable
internal data class ImgurResponse(
    val data: List<ImgurData>?,
    val success: Boolean?,
    val status: Int?,
)

/**
 * Error response from the Imgur API
 */
@Serializable
internal data class ImgurErrorResponse(
    val data: ImgurErrorData?,
    val success: Boolean?,
    val status: Int?,
)

/**
 * Error data from the Imgur API
 */
@Serializable
internal data class ImgurErrorData(
    val error: String?,
    val request: String?,
    val method: String?,
)

/**
 * Data from the Imgur API
 */
@Serializable
internal data class ImgurData(
    val id: String?,
    val title: String?,
    val description: String?,
    val datetime: Long?,
    val cover: String?,
    val cover_width: Int?,
    val cover_height: Int?,
    val account_url: String?,
    val account_id: Long?,
    val privacy: String?,
    val layout: String?,
    val views: Int?,
    val link: String?,
    val ups: Int?,
    val downs: Int?,
    val points: Int?,
    val score: Int?,
    val is_album: Boolean?,
    val vote: String?,
    val favorite: Boolean?,
    val nsfw: Boolean?,
    val section: String?,
    val comment_count: Int?,
    val favorite_count: Int?,
    val topic: String?,
    val topic_id: Int?,
    val images_count: Int?,
    val in_gallery: Boolean?,
    val is_ad: Boolean?,
    val tags: List<Tag>?,
    val ad_type: Int?,
    val ad_url: String?,
    val in_most_viral: Boolean?,
    val include_album_ads: Boolean?,
    val images: List<Image>?,
    val ad_config: AdConfig?,
    val error: String?,
)

/**
 * Tag from the Imgur API
 */
@Serializable
internal data class Tag(
    val name: String?,
    val display_name: String?,
    val followers: Int?,
    val total_items: Int?,
    val following: Boolean?,
    val is_whitelisted: Boolean?,
    val background_hash: String?,
    val thumbnail_hash: String?,
    val accent: String?,
    val background_is_animated: Boolean?,
    val thumbnail_is_animated: Boolean?,
    val is_promoted: Boolean?,
    val description: String?,
    val logo_hash: String?,
    val logo_destination_url: String?,
    val description_annotations: Map<String, @Contextual Any>?,
)

/**
 * Image from the Imgur API
 */
@Serializable
internal data class Image(
    val id: String?,
    val title: String?,
    val description: String?,
    val datetime: Long?,
    val type: String?,
    val animated: Boolean?,
    val width: Int?,
    val height: Int?,
    val size: Int?,
    val views: Int?,
    val bandwidth: Long?,
    val vote: String?,
    val favorite: Boolean?,
    val nsfw: Boolean?,
    val section: String?,
    val account_url: String?,
    val account_id: String?,
    val is_ad: Boolean?,
    val in_most_viral: Boolean?,
    val has_sound: Boolean?,
    val tags: List<Tag>?,
    val ad_type: Int?,
    val ad_url: String?,
    val edited: String?,
    val in_gallery: Boolean?,
    val link: String?,
    val mp4_size: Int?,
    val mp4: String?,
    val gifv: String?,
    val hls: String?,
    val processing: Processing?,
    val comment_count: Int?,
    val favorite_count: Int?,
    val ups: Int?,
    val downs: Int?,
    val points: Int?,
    val score: Int?,
)

/**
 * Processing from the Imgur API
 */
@Serializable
internal data class Processing(
    val status: String?,
)

/**
 * Ad configuration from the Imgur API
 */
@Serializable
internal data class AdConfig(
    val safeFlags: List<String>?,
    val highRiskFlags: List<String>?,
    val unsafeFlags: List<String>?,
    val wallUnsafeFlags: List<String>?,
    val showsAds: Boolean?,
    val showAdLevel: Int?,
    val nsfw_score: Double?,
)
