package com.ma.streamview.data.model.gql.category


import com.ma.streamview.data.model.gql.common.Streams
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryResponse(
    @SerialName("streams")
    val streams: Streams? = null,
    @SerialName("videos")
    val videos: Videos? = null
)