package com.ma.streamview.data.model.gql.search


import com.ma.streamview.data.model.gql.common.StreamEdge
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchStreams(
    @SerialName("edges")
    val streamEdges: List<StreamEdge>,
    @SerialName("pageInfo")
    val pageInfo: PageInfo
)