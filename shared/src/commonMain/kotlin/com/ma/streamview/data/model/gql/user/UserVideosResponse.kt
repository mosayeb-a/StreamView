package com.ma.streamview.data.model.gql.user


import com.ma.streamview.data.model.gql.search.Extensions
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserVideosResponse(
    @SerialName("data")
    val `data`: UserVideoData,
    @SerialName("extensions")
    val extensions: Extensions
)