package com.ma.streamview.data.model

import kotlinx.serialization.Serializable

@kotlinx.serialization.Serializable
data class GqlVideoTokenResponse(
    val data: Data
)

@kotlinx.serialization.Serializable
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