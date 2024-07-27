package com.ma.streamview.data.model.gql.common


import com.ma.streamview.data.model.gql.common.StreamEdge
import com.ma.streamview.data.model.gql.search.PageInfo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Streams(
    @SerialName("edges")
    val edges: List<StreamEdge>,
    @SerialName("pageInfo")
    val pageInfo: PageInfo
)