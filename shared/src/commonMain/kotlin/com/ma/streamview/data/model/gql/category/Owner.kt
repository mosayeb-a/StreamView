package com.ma.streamview.data.model.gql.category


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Owner(
    @SerialName("displayName")
    val displayName: String,
    @SerialName("id")
    val id: String,
    @SerialName("login")
    val login: String,
    @SerialName("profileImageURL")
    val profileImageURL: String
)