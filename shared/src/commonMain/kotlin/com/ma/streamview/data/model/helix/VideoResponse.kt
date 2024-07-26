package com.ma.streamview.data.model.helix


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VideoResponse(
    @SerialName("data")
    val `data`: List<Video>,
    @SerialName("pagination")
    val pagination: Pagination
)