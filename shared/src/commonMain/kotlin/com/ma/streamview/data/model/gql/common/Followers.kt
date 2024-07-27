package com.ma.streamview.data.model.gql.common


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Followers(
    @SerialName("totalCount")
    val totalCount: Int
)