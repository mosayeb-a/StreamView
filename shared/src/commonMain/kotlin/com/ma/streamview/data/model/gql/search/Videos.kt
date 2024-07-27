package com.ma.streamview.data.model.gql.search


import com.ma.streamview.data.model.gql.common.VideoNode
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Videos(
    @SerialName("cursor")
    val cursor: String,
    @SerialName("items")
    val items: List<VideoNode>,
    @SerialName("pageInfo")
    val pageInfo: PageInfo
)