package com.ma.streamview.data.model.gql.common


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryEdge(
    @SerialName("cursor")
    val cursor: String,
    @SerialName("node")
    val categoryNode: CategoryNode
)