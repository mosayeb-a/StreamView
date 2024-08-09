package com.ma.streamview.data.model

import kotlinx.serialization.Serializable

@Serializable
data class GqlPlaybackTokenResponse(
    val data: Data
)

    @Serializable
data class Data(
    val videoPlaybackAccessToken: AccessToken? = null,
    val streamPlaybackAccessToken: AccessToken? = null
)

@Serializable
data class AccessToken(
    val value: String,
    val signature: String,
    val __typename: String
)