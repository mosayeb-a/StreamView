package com.ma.streamview.data.model.gql.common


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Broadcaster(
    @SerialName("broadcastSettings")
    val broadcastSettings: BroadcastSettings,
    @SerialName("displayName")
    val displayName: String? = null,
    @SerialName("id")
    val id: String? = null,
    @SerialName("login")
    val login: String? = null,
    @SerialName("profileImageURL")
    val profileImageURL: String? = null
)