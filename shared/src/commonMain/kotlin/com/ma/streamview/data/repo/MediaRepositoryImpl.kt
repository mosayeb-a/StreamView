package com.ma.streamview.data.repo

import com.ma.streamview.data.model.TokenContainer
import com.ma.streamview.data.model.gql.GQLResponse
import com.ma.streamview.data.model.gql.user.UserNode
import com.ma.streamview.data.model.gql.user.UserVideosResponse
import com.ma.streamview.data.model.helix.StreamResponse
import com.ma.streamview.data.model.helix.VideoResponse
import com.ma.streamview.data.repo.source.Channel
import com.ma.streamview.data.repo.source.MediaDataSource
import com.ma.streamview.data.repo.source.Watched_video
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class MediaRepositoryImpl(
    private val remoteDataSource: MediaDataSource,
    private val localDataSource: MediaDataSource
) : MediaRepository {

    override suspend fun searchVideos(cursor: String, query: String): GQLResponse =
        remoteDataSource.searchVideos(cursor = cursor, query = query)

    override suspend fun searchStreams(query: String, first: Int, after: Int?): GQLResponse =
        remoteDataSource.searchStreams(query = query, first = first, after = after)

    override suspend fun searchChannels(query: String, first: Int, after: Int?): GQLResponse =
        remoteDataSource.searchChannels(query = query, first = first, after = after)

    override suspend fun searchCategories(query: String, first: Int, after: Int?): GQLResponse =
        remoteDataSource.searchCategories(query = query, first = first, after = after)

    override suspend fun getTopStream(first: Int, after: Int?, tags: List<String>?): GQLResponse =
        remoteDataSource.getTopStream(first = first, after = after, tags = tags)

    override suspend fun getStreamsByGameId(
        id: String,
        sort: String,
        tags: List<String>?,
        first: Int,
        after: Int?
    ): GQLResponse =
        remoteDataSource.getStreamsByGameId(
            id = id,
            sort = sort,
            tags = tags,
            first = first,
            after = after
        )

    override suspend fun getVideosByGameId(
        id: String,
        sort: String,
        tags: List<String>?,
        first: Int,
        after: Int?
    ): GQLResponse = remoteDataSource.getVideosByGameId(
        id = id,
        sort = sort,
        tags = tags,
        first = first,
        after = after
    )

    override suspend fun getTopCategories(
        first: Int,
        after: Int?,
        tags: List<String>?
    ): GQLResponse = remoteDataSource.getTopCategories()

    override suspend fun getUserStreams(userIds: List<String>): GQLResponse =
        remoteDataSource.getUserStreams(userIds)

    override suspend fun getUserVideos(
        userId: String,
        first: Int,
        after: Int?,
        tags: List<String>?,
        sort: String
    ): UserVideosResponse =
        remoteDataSource.getUserVideos(
            userId = userId,
            first = first,
            after = after,
            tags = tags,
            sort = sort
        )

    override suspend fun getUser(login: String): GQLResponse =
        remoteDataSource.getUser(login)

    override suspend fun getVideos(ids: List<String>): VideoResponse =
        remoteDataSource.getVideos(ids)

    override suspend fun getStreams(userIds: List<String>): StreamResponse =
        remoteDataSource.getStreams(userIds)

    override suspend fun getWatchedList(): List<Watched_video> =
        localDataSource.getWatchedList()

    override suspend fun addToWatchedList(video: Watched_video, maxWatchedPosition: Long) {
        localDataSource.addToWatchedList(video, maxWatchedPosition)
    }

    override suspend fun removeFromWatchedList(id: String) {
        localDataSource.removeFromWatchedList(id)
    }

    override suspend fun getFollowedChannels(): List<UserNode> {
        return localDataSource.getFollowedChannels()
    }

    override suspend fun followChannel(channel: Channel) {
        return localDataSource.followChannel(channel)
    }

    override suspend fun unfollowChannel(id: String) =
        localDataSource.unfollowChannel(id)

    override suspend fun getChannelById(id: String): UserNode =
        localDataSource.getChannelById(id)

    override suspend fun isUserAuthenticated(): Boolean {
        if (!TokenContainer.token.isNullOrEmpty()) {
            return true
        }
        localDataSource.loadToken()
        if (!TokenContainer.token.isNullOrEmpty()) {
            return true
        }
        val tokenResponse = remoteDataSource.getAccessToken()
        localDataSource.saveToken(tokenResponse.accessToken)
        TokenContainer.update(tokenResponse.accessToken)
        return !TokenContainer.token.isNullOrEmpty()
    }

    override suspend fun getPlaybackUrl(vodId: String?, channelName: String?): String {
        return remoteDataSource.getPlaybackUrl(vodId = vodId, channelName = channelName)
    }

    override fun loadUserToken() {
        return localDataSource.loadToken()
    }


    override suspend fun getSearchHistory(): Flow<List<String>> {
        return flow {
            emit(listOf("zuh", "sara", "coding", "golang"))
        }
    }
}


