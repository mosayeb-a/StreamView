package com.ma.streamview.services

import com.ma.streamview.data.model.GqlPlaybackTokenResponse
import com.ma.streamview.data.model.gql.GQLResponse
import com.ma.streamview.data.model.gql.user.UserVideosResponse


interface GQLService {
    suspend fun getPlaybackAccessToken(
        isLive: Boolean,
        isVod: Boolean,
        channelName: String?,
        vodID: String?,
        playerType: String
    ): GqlPlaybackTokenResponse

    suspend fun searchVideos(cursor: String, query: String): GQLResponse
    suspend fun searchStreams(query: String, first: Int = 30, after: Int? = null): GQLResponse
    suspend fun searchChannels(query: String, first: Int = 30, after: Int? = null): GQLResponse
    suspend fun searchCategories(query: String, first: Int = 30, after: Int? = null): GQLResponse

    suspend fun getStreamsByGameId(
        id: String,
        sort: String = "VIEWER_COUNT",
        tags: List<String>? = null,
        first: Int = 30,
        after: Int? = null
    ): GQLResponse

    suspend fun getVideosByGameId(
        id: String,
        sort: String = "VIEWER_COUNT",
        tags: List<String>? = null,
        first: Int = 30,
        after: Int? = null
    ): GQLResponse

    suspend fun getTopCategories(
        first: Int = 30,
        after: Int? = null,
        tags: List<String>? = null
    ): GQLResponse

    suspend fun getTopStream(
        first : Int ,
        after : Int ? =null,
        tags : List<String>? = null
    ): GQLResponse

    suspend fun getUserStreams(userIds: List<String>): GQLResponse
    suspend fun getUser(login: String): GQLResponse
    suspend fun getUserVideos(
        userId: String,
        first: Int,
        after: Int? = null,
        tags: List<String>? = null,
        sort: String = "TIME"
    ): UserVideosResponse
}

