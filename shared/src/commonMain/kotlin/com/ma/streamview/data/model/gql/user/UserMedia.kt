package com.ma.streamview.data.model.gql.user


import com.ma.streamview.data.model.gql.common.StreamNode
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserMedia(
    @SerialName("displayName")
    val displayName: String? = null,
    @SerialName("id")
    val id: String? = null,
    @SerialName("login")
    val login: String? = null,
    @SerialName("profileImageURL")
    val profileImageURL: String? = null,
    @SerialName("stream")
    val stream: StreamNode? = null,
    @SerialName("videos")
    val videos: Videos? = null
)