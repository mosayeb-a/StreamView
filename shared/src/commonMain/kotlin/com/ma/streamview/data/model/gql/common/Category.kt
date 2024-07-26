package com.ma.streamview.data.model.gql.common


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Category(
    @SerialName("displayName")
    val displayName: String,
    @SerialName("id")
    val id: String,
    @SerialName("slug")
    val slug: String
)