package com.ma.streamview.data.repo.source

import com.ma.streamview.data.model.TokenResponse
import com.ma.streamview.data.model.gql.GQLResponse
import com.ma.streamview.data.model.gql.user.UserNode
import com.ma.streamview.data.model.gql.user.UserVideosResponse
import com.ma.streamview.data.model.helix.StreamResponse
import com.ma.streamview.data.model.helix.VideoResponse

interface MediaDataSource {
    fun saveToken(token: String)
    fun loadToken()
    suspend fun getAccessToken(): TokenResponse

    suspend fun getPlaybackUrl(vodId: String?, channelName: String?): String


    // local stuff
    suspend fun getFollowedChannels(): List<UserNode>
    suspend fun followChannel(channel: Channel)
    suspend fun unfollowChannel(id: String)
    suspend fun getChannelById(id: String): UserNode

    suspend fun getWatchedList(): List<Watched_video>
    suspend fun addToWatchedList(video: Watched_video, maxWatchedPosition: Long)
    suspend fun removeFromWatchedList(id: String)

    // remote stuff
    suspend fun getTopStream(
        first: Int,
        after: Int? = null,
        tags: List<String>? = null
    ): GQLResponse

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

    suspend fun getUserStreams(userIds: List<String>): GQLResponse
    suspend fun getUser(login: String): GQLResponse

    suspend fun getUserVideos(
        userId: String,
        first: Int,
        after: Int? = null,
        tags: List<String>? = null,
        sort: String = "TIME"
    ): UserVideosResponse

    suspend fun getVideos(ids: List<String>): VideoResponse
    suspend fun getStreams(userIds: List<String>): StreamResponse

}