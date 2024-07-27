package com.ma.streamview.data.model.gql.search


import com.ma.streamview.data.model.gql.common.CategoryEdge
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchCategories(
    @SerialName("edges")
    val categoryEdges: List<CategoryEdge>,
    @SerialName("pageInfo")
    val pageInfo: PageInfo
)