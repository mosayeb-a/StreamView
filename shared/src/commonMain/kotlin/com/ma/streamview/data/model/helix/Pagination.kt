package com.ma.streamview.data.model.helix


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Pagination(
    @SerialName("cursor")
    val cursor: String? = null
)