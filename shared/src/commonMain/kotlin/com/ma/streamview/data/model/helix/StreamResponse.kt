package com.ma.streamview.data.model.helix


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StreamResponse(
    @SerialName("data")
    val `data`: List<Stream>,
    @SerialName("pagination")
    val pagination: Pagination
)