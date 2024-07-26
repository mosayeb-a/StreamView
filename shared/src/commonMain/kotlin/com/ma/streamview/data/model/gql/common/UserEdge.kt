package com.ma.streamview.data.model.gql.common


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserEdge(
    @SerialName("cursor")
    val cursor: String,
    @SerialName("node")
    val node: User
)