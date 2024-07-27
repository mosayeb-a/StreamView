package com.ma.streamview.data.model.gql.user


import com.ma.streamview.data.model.gql.common.Followers
import com.ma.streamview.data.model.gql.common.StreamNode
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserNode(
    @SerialName("bannerImageURL")
    val bannerImageURL: String,
    @SerialName("createdAt")
    val createdAt: String,
    @SerialName("displayName")
    val displayName: String,
    @SerialName("followers")
    val followers: Followers,
    @SerialName("id")
    val id: String,
    @SerialName("lastBroadcast")
    val lastBroadcast: LastBroadcast,
    @SerialName("login")
    val login: String,
    @SerialName("profileImageURL")
    val profileImageURL: String,
    @SerialName("roles")
    val roles: Roles,
    @SerialName("stream")
    val stream: StreamNode? =null
)