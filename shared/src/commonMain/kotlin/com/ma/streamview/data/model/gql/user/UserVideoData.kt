package com.ma.streamview.data.model.gql.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class UserVideoData(
    @SerialName("user")
    val userMedia: UserMedia
)