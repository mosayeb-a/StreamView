package com.ma.streamview.data.model.gql.search


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Extensions(
    @SerialName("durationMilliseconds")
    val durationMilliseconds: Int,
    @SerialName("operationName")
    val operationName: String? = null,
    @SerialName("requestID")
    val requestID: String
)