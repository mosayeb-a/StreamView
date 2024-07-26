package com.ma.streamview.data.model.gql.common


import com.ma.streamview.TwitchHelper
import com.ma.streamview.data.model.gql.category.Owner
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VideoNode(
    @SerialName("animatedPreviewURL")
    val animatedPreviewURL: String,
    @SerialName("broadcastType")
    val broadcastType: String,
    @SerialName("contentTags")
    val contentTags: List<String>? = null,
    @SerialName("createdAt")
    val createdAt: String,
    @SerialName("id")
    val id: String,
    val game: Category? = null,
    @SerialName("lengthSeconds")
    val lengthSeconds: Int,
    @SerialName("owner")
    val owner: Owner? = null,
    @SerialName("previewThumbnailURL")
    val previewThumbnailURL: String,
    @SerialName("title")
    val title: String,
    @SerialName("viewCount")
    val viewCount: Int
) {
    val thumb: String
        get() = TwitchHelper.getTemplateUrl(previewThumbnailURL, "video") ?: ""
}