package com.ma.streamview.data.model.gql


import com.ma.streamview.data.model.gql.common.Data
import com.ma.streamview.data.model.gql.search.Extensions
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GQLResponse(
    @SerialName("data")
    val data: Data?=null,
    @SerialName("extensions")
    val extensions: Extensions?=null
)