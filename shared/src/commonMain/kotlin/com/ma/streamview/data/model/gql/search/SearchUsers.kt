package com.ma.streamview.data.model.gql.search


import com.ma.streamview.data.model.gql.common.UserEdge
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchUsers(
    @SerialName("edges")
    val edges: List<UserEdge>,
    @SerialName("pageInfo")
    val pageInfo: PageInfo
)