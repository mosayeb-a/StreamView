package com.ma.streamview.data.model.gql.common


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VideoEdge(
    @SerialName("cursor")
    val cursor: String? = null,
    @SerialName("node")
    val videoNode: VideoNode
)