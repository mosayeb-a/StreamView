package com.ma.streamview.data.model.gql.common


import com.ma.streamview.data.model.gql.category.CategoryResponse
import com.ma.streamview.data.model.gql.category.top.Games
import com.ma.streamview.data.model.gql.search.SearchCategories
import com.ma.streamview.data.model.gql.search.SearchFor
import com.ma.streamview.data.model.gql.search.SearchStreams
import com.ma.streamview.data.model.gql.search.SearchUsers
import com.ma.streamview.data.model.gql.user.UserMedia
import com.ma.streamview.data.model.gql.user.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Data(
    @SerialName("searchStreams")
    val searchStreams: SearchStreams? = null,
    @SerialName("searchUsers")
    val searchUsers: SearchUsers? = null,
    @SerialName("searchFor")
    val searchFor: SearchFor? = null,
    @SerialName("searchCategories")
    val searchCategories: SearchCategories? = null,
    @SerialName("game")
    val categoryMedia: CategoryResponse? = null,
    @SerialName("games")
    val topGames: Games? = null,
    @SerialName("users")
    val userStreams: List<UserMedia>? = null,
    @SerialName("user")
    val getUser: User? = null,
    @SerialName("streams")
    val topStreams: Streams? = null
)