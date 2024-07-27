package com.ma.streamview.data.model.gql.common


import com.ma.streamview.TwitchHelper
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StreamNode(
    @SerialName("broadcaster")
    val broadcaster: Broadcaster,
    @SerialName("createdAt")
    val createdAt: String,
    @SerialName("freeformTags")
    val freeformTags: List<FreeformTag>? = emptyList(),
    @SerialName("game")
    val category: Category? = null,
    @SerialName("id")
    val id: String,
    @SerialName("previewImageURL")
    val previewImageURL: String,
    @SerialName("type")
    val type: String,
    @SerialName("viewersCount")
    val viewersCount: Int
) {
    val preview = TwitchHelper.getTemplateUrl(previewImageURL, "video") ?: ""
}