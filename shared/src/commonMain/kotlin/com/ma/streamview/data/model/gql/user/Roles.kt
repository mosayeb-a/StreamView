package com.ma.streamview.data.model.gql.user


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Roles(
    @SerialName("isAffiliate")
    val isAffiliate: Boolean,
    @SerialName("isGlobalMod")
    val isGlobalMod: Boolean? = null,
    @SerialName("isPartner")
    val isPartner: Boolean,
    @SerialName("isSiteAdmin")
    val isSiteAdmin: Boolean? = null,
    @SerialName("isStaff")
    val isStaff: Boolean? = null
)