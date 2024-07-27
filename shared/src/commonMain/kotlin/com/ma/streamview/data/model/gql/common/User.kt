package com.ma.streamview.data.model.gql.common


import com.ma.streamview.TwitchHelper
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @SerialName("displayName")
    val displayName: String,
    @SerialName("followers")
    val followers: Followers,
    @SerialName("id")
    val id: String,
    @SerialName("login")
    val login: String,
    @SerialName("profileImageURL")
    val profileImageURL: String,
    @SerialName("stream")
    val stream: String? = null
)