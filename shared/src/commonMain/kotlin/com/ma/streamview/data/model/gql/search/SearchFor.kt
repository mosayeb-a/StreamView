package com.ma.streamview.data.model.gql.search


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchFor(
    @SerialName("videos")
    val videos: Videos
)