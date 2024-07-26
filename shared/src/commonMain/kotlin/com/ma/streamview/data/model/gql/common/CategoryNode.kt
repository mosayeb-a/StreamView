package com.ma.streamview.data.model.gql.common


import com.ma.streamview.TwitchHelper
import com.ma.streamview.data.model.gql.search.Tag
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryNode(
    @SerialName("boxArtURL")
    private val boxArtURL: String,
    @SerialName("broadcastersCount")
    val broadcastersCount: Int? = null,
    @SerialName("displayName")
    val displayName: String,
    @SerialName("id")
    val id: String,
    @SerialName("slug")
    val slug: String,
    @SerialName("tags")
    val tags: List<Tag>,
    @SerialName("viewersCount")
    val viewersCount: Int? = null
){
    val boxArtURLPreview
        get() = TwitchHelper.getTemplateUrl(boxArtURL,"game") ?: ""
}