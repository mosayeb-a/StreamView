package com.ma.streamview.data.model.gql.user


import com.ma.streamview.data.model.gql.common.BroadcastSettings
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserBroadcaster(
    @SerialName("broadcastSettings")
    val broadcastSettings: BroadcastSettings
)