package com.ma.streamview.data.model.gql.category


import com.ma.streamview.data.model.gql.common.VideoEdge
import com.ma.streamview.data.model.gql.search.PageInfo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Videos(
    @SerialName("edges")
    val videoEdges: List<VideoEdge>,
    @SerialName("pageInfo")
    val pageInfo: PageInfo
)