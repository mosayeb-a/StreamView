package com.ma.streamview.data.model.gql.search


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Tag(
    @SerialName("id")
    val id: String,
    @SerialName("localizedName")
    val localizedName: String
)