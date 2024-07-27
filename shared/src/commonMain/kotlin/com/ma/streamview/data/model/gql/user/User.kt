package com.ma.streamview.data.model.gql.user


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @SerialName("description")
    val description: String? = null,
    @SerialName("displayName")
    val displayName: String? = null,
    @SerialName("followers")
    val followers: Followers? = null,
    @SerialName("id")
    val id: String? = null,
    @SerialName("bannerImageURL")
    val bannerImageURL: String? = null,
    @SerialName("profileImageURL")
    val profileImageURL: String? = null
)