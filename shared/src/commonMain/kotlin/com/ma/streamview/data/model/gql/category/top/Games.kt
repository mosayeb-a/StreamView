package com.ma.streamview.data.model.gql.category.top


import com.ma.streamview.data.model.gql.common.CategoryEdge
import com.ma.streamview.data.model.gql.search.PageInfo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Games(
    @SerialName("edges")
    val categoryEdges: List<CategoryEdge>,
    @SerialName("pageInfo")
    val pageInfo: PageInfo
)