package com.ma.streamview.data.repo.source

import com.ma.streamview.ACCESS_TOKEN_KEY
import com.ma.streamview.data.model.TokenContainer
import com.ma.streamview.data.model.TokenResponse
import com.ma.streamview.data.model.gql.GQLResponse
import com.ma.streamview.data.model.gql.common.Followers
import com.ma.streamview.data.model.gql.user.LastBroadcast
import com.ma.streamview.data.model.gql.user.Roles
import com.ma.streamview.data.model.gql.user.UserNode
import com.ma.streamview.data.model.gql.user.UserVideosResponse
import com.ma.streamview.data.model.helix.StreamResponse
import com.ma.streamview.data.model.helix.VideoResponse
import com.ma.streamview.data.preferences.PlatformPreferences
import com.ma.streamview.database.StreamDatabase



fun Channel.user(): UserNode = UserNode(
    bannerImageURL = "",
    createdAt = "",
    displayName = userName,
    followers = Followers(totalCount = 0),
    id = id,
    lastBroadcast = LastBroadcast(startedAt = "dicam"),
    login = userLogin,
    profileImageURL = channelLogo,
    roles = Roles(
        isAffiliate = false,
        isGlobalMod = null,
        isPartner = false,
        isSiteAdmin = null,
        isStaff = null
    ),
    stream = null
)


class MediaLocalDataSource(
    private val preferences: PlatformPreferences,
    db: StreamDatabase
) : MediaDataSource {

    private val followsChannelQueries = db.follows_channelQueries
    private val watchedVideoQueries = db.watched_videoQueries


    override suspend fun getWatchedList(): List<Watched_video> {
        return watchedVideoQueries.getWatchedList()
            .executeAsList()
    }

    override suspend fun addToWatchedList(video: Watched_video, maxWatchedPosition: Long) {
        watchedVideoQueries.addToWatchedList(
            id = video.id,
            userId = video.userId,
            maxPositionSeen = maxWatchedPosition,
            slug = video.slug
        )
    }

    override suspend fun removeFromWatchedList(id: String) {
        watchedVideoQueries.removeFromWatchedList(id)
    }

    override suspend fun getFollowedChannels(): List<UserNode> {
        return followsChannelQueries.getChannels()
            .executeAsList()
            .map { it.user() }
    }

    override suspend fun followChannel(channel: Channel) {
        followsChannelQueries.followChannel(
            id = channel.id,
            userName = channel.userName,
            userLogin = channel.userLogin,
            channelLogo = channel.channelLogo
        )
    }

    override suspend fun unfollowChannel(id: String) {
        followsChannelQueries.unfollowChannel(id = id)
    }

    override suspend fun getChannelById(id: String): UserNode {
        return followsChannelQueries.getChannelById(id)
            .executeAsOne()
            .let {
                UserNode(
                    displayName = it.userName,
                    id = it.id,
                    login = it.userLogin,
                    profileImageURL = it.channelLogo,
                    bannerImageURL = "",
                    createdAt = "",
                    followers = Followers(totalCount = 0),
                    lastBroadcast = LastBroadcast(startedAt = ""),
                    roles = Roles(
                        isAffiliate = false,
                        isGlobalMod = null,
                        isPartner = false,
                        isSiteAdmin = null,
                        isStaff = null
                    ),
                    stream = null,
                )
            }
    }

    override fun saveToken(token: String) {
        preferences.put(ACCESS_TOKEN_KEY, token)
    }

    override fun loadToken() {
        TokenContainer.update(preferences.getString(ACCESS_TOKEN_KEY))
//        preferences.getString(ACCESS_TOKEN_KEY)?.let { TokenContainer.setToken(token = it) }
    }

    override suspend fun getAccessToken(): TokenResponse {
        TODO("Not yet implemented")
    }


    override suspend fun getPlaybackUrl(vodId: String, channelName: String): String {
        TODO("Not yet implemented")
    }


    override suspend fun getPlaybackUrl(id: String): String {
        TODO("Not yet implemented")
    }

    override suspend fun searchVideos(cursor: String, query: String): GQLResponse {
        TODO("Not yet implemented")
    }

    override suspend fun searchStreams(query: String, first: Int, after: Int?): GQLResponse {
        TODO("Not yet implemented")
    }

    override suspend fun searchChannels(query: String, first: Int, after: Int?): GQLResponse {
        TODO("Not yet implemented")
    }

    override suspend fun searchCategories(query: String, first: Int, after: Int?): GQLResponse {
        TODO("Not yet implemented")
    }

    override suspend fun getStreamsByGameId(
        id: String,
        sort: String,
        tags: List<String>?,
        first: Int,
        after: Int?
    ): GQLResponse {
        TODO("Not yet implemented")
    }

    override suspend fun getVideosByGameId(
        id: String,
        sort: String,
        tags: List<String>?,
        first: Int,
        after: Int?
    ): GQLResponse {
        TODO("Not yet implemented")
    }

    override suspend fun getTopCategories(
        first: Int,
        after: Int?,
        tags: List<String>?
    ): GQLResponse {
        TODO("Not yet implemented")
    }

    override suspend fun getUserStreams(userIds: List<String>): GQLResponse {
        TODO("Not yet implemented")
    }

    override suspend fun getUser(login: String): GQLResponse {
        TODO("Not yet implemented")
    }

    override suspend fun getUserVideos(
        userId: String,
        first: Int,
        after: Int?,
        tags: List<String>?,
        sort: String
    ): UserVideosResponse {
        TODO("Not yet implemented")
    }

    override suspend fun getVideos(ids: List<String>): VideoResponse {
        TODO("Not yet implemented")
    }

    override suspend fun getTopStream(first: Int, after: Int?, tags: List<String>?): GQLResponse {
        TODO("Not yet implemented")
    }

    override suspend fun getStreams(userIds: List<String>): StreamResponse {
        TODO("Not yet implemented")
    }

}