package com.ma.streamview.data.model.gql.user


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LastBroadcast(
    @SerialName("startedAt")
    val startedAt: String
)