package com.ma.streamview.data.repo.source

import com.ma.streamview.data.model.TokenResponse
import com.ma.streamview.data.model.gql.GQLResponse
import com.ma.streamview.data.model.gql.user.UserNode
import com.ma.streamview.data.model.gql.user.UserVideosResponse
import com.ma.streamview.data.model.helix.StreamResponse
import com.ma.streamview.data.model.helix.VideoResponse
import com.ma.streamview.services.GQLService
import com.ma.streamview.services.HelixService
import io.ktor.http.URLBuilder
import io.ktor.http.Url
import kotlin.random.Random

class MediaRemoteDataSource(
    private val helixService: HelixService,
    private val gqlService: GQLService
) : MediaDataSource {

    override suspend fun getStreams(userIds: List<String>): StreamResponse =
        helixService.getStreams(userIds)

    override suspend fun getVideos(ids: List<String>): VideoResponse =
        helixService.getVideos(ids)

    override suspend fun searchVideos(cursor: String, query: String): GQLResponse =
        gqlService.searchVideos(cursor = cursor, query = query)

    override suspend fun searchStreams(query: String, first: Int, after: Int?): GQLResponse =
        gqlService.searchStreams(query = query, first = first, after = after)

    override suspend fun searchChannels(query: String, first: Int, after: Int?): GQLResponse =
        gqlService.searchChannels(query = query, first = first, after = after)

    override suspend fun searchCategories(query: String, first: Int, after: Int?): GQLResponse =
        gqlService.searchCategories(query = query, first = first, after = after)

    override suspend fun getTopStream(first: Int, after: Int?, tags: List<String>?): GQLResponse =
        gqlService.getTopStream(first = first, after = after, tags = tags)

    override suspend fun getStreamsByGameId(
        id: String,
        sort: String,
        tags: List<String>?,
        first: Int,
        after: Int?
    ): GQLResponse = gqlService.getStreamsByGameId(
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
    ): GQLResponse = gqlService.getVideosByGameId(
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
    ): GQLResponse = gqlService.getTopCategories()

    override suspend fun getUserStreams(userIds: List<String>): GQLResponse =
        gqlService.getUserStreams(userIds = userIds)

    override suspend fun getUser(login: String): GQLResponse =
        gqlService.getUser(login = login)


    override suspend fun getUserVideos(
        userId: String,
        first: Int,
        after: Int?,
        tags: List<String>?,
        sort: String
    ): UserVideosResponse = gqlService.getUserVideos(
        userId = userId,
        first = first,
        after = after,
        tags = tags,
        sort = sort
    )

    override suspend fun getAccessToken(): TokenResponse =
        helixService.getAccessToken()

//    override suspend fun getPlaybackUrl(
//        vodId: String?,
//        userLogin: String?
//    ): String {
//        val supportedCodecs = "av1,h265,h264"
//        val accessToken = if (vodId != null) {
//            gqlService.getPlaybackAccessToken(
//                isLive = false,
//                isVod = true,
//                channelName = null,
//                vodID = vodId,
//                playerType = "channel_home_live"
//            ).data.videoPlaybackAccessToken
//        } else {
//            gqlService.getPlaybackAccessToken(
//                isLive = true,
//                isVod = false,
//                channelName = userLogin,
//                vodID = null,
//                playerType = "site"
//            ).data.streamPlaybackAccessToken
//        }

//        if (vodId != null){
//            val url = buildUrl(
//                "https://twitch-downloader-proxy.twitcharchives.workers.dev/$vodId.m3u8",
//                "allow_source" to "true",
//                "allow_audio_only" to "true",
//                "player_backend" to "mediaplayer",
//                "platform" to if (supportedCodecs.contains("av1", true)) "web" else null,
//                "sig" to accessToken?.signature,
//                "supported_codecs" to supportedCodecs,
//                "playlist_include_framerate" to "true",
//                "token" to accessToken?.value
//            )
//        }
//
//        println("mylog: loadVideoUrl-> url: $url")
//        println("getPlaybackAccessToken: " + accessToken)
//        return url.toString()
//    }
//    suspend fun loadStreamPlaylistUrl(
//        gqlHeaders: Map<String, String>,
//        channelLogin: String,
//        randomDeviceId: Boolean?,
//        xDeviceId: String?,
//        playerType: String?,
//        supportedCodecs: String?,
//        proxyPlaybackAccessToken: Boolean,
//        proxyMultivariantPlaylist: Boolean,
//        proxyHost: String?,
//        proxyPort: Int?,
//        proxyUser: String?,
//        proxyPassword: String?
//    ): String = withContext(Dispatchers.IO) {
//        val accessToken = if (proxyPlaybackAccessToken && !proxyHost.isNullOrBlank() && proxyPort != null) {
//            val json = JsonObject().apply {
//                add("extensions", JsonObject().apply {
//                    add("persistedQuery", JsonObject().apply {
//                        addProperty("sha256Hash", "3093517e37e4f4cb48906155bcd894150aef92617939236d2508f3375ab732ce")
//                        addProperty("version", 1)
//                    })
//                })
//                addProperty("operationName", "PlaybackAccessToken")
//                add("variables", JsonObject().apply {
//                    addProperty("isLive", true)
//                    addProperty("login", channelLogin)
//                    addProperty("isVod", false)
//                    addProperty("vodID", "")
//                    addProperty("playerType", playerType)
//                })
//            }
//            val text = getResponse(
//                url = "https://gql.twitch.tv/gql/",
//                body = json.toString().toRequestBody(),
//                headers = accessTokenHeaders.filterKeys { it == C.HEADER_CLIENT_ID || it == "X-Device-Id" },
//                proxyHost = proxyHost,
//                proxyPort = proxyPort,
//                proxyUser = proxyUser,
//                proxyPassword = proxyPassword
//            )
//            val data = if (text.isNotBlank()) JSONObject(text).optJSONObject("data") else null
//            val message = data?.optJSONObject("streamPlaybackAccessToken")
//            PlaybackAccessToken(
//                token = message?.optString("value"),
//                signature = message?.optString("signature"),
//            )
//        } else {
//            graphQL.loadPlaybackAccessToken(
//                headers = accessTokenHeaders,
//                login = channelLogin,
//                playerType = playerType
//            ).streamToken
//        }
//        val url = buildUrl(
//            "https://usher.ttvnw.net/api/channel/hls/$channelLogin.m3u8?",
//            "allow_source", "true",
//            "allow_audio_only", "true",
//            "fast_bread", "true", //low latency
//            "p", Random.nextInt(9999999).toString(),
//            "platform", if (supportedCodecs?.contains("av1", true) == true) "web" else null,
//            "sig", accessToken?.signature,
//            "supported_codecs", supportedCodecs,
//            "token", accessToken?.token
//        ).toString()
//
//
//        println("mylog: loadStreamUrl-> url: $url")
//        if (proxyMultivariantPlaylist) {
//            val response = getResponse(
//                url = url,
//                proxyHost = proxyHost,
//                proxyPort = proxyPort,
//                proxyUser = proxyUser,
//                proxyPassword = proxyPassword
//            )
//            Base64.encodeToString(response.toByteArray(), Base64.DEFAULT)
//        } else url
//    }


    override suspend fun getPlaybackUrl(vodId: String?, channelName: String?): String {
        println("streamURLTag. in getPlaybackUrl")
        val supportedCodecs = "av1,h265,h264"
        val accessToken = if (vodId != null) {
            gqlService.getPlaybackAccessToken(
                isLive = false,
                isVod = true,
                channelName = null,
                vodID = vodId,
                playerType = "channel_home_live"
            ).data.videoPlaybackAccessToken
        } else {
            gqlService.getPlaybackAccessToken(
                isLive = true,
                isVod = false,
                channelName = channelName,
                vodID = null,
                playerType = "site"
            ).data.streamPlaybackAccessToken
        }
        println("streamURLTag. accessToken: $accessToken")
        val baseUrl = if (vodId != null) {
            "https://usher.ttvnw.net/vod/$vodId.m3u8"
        } else {
            "https://usher.ttvnw.net/api/channel/hls/$channelName.m3u8"
        }
        println("streamURLTag. baseUrl: $baseUrl")

        val queryParams = listOfNotNull(
            "allow_source" to "true",
            "allow_audio_only" to "true",
            "p" to Random.nextInt(9999999).toString(),
            "platform" to if (supportedCodecs.contains("av1", true)) "web" else null,
            "sig" to accessToken?.signature,
            "supported_codecs" to supportedCodecs,
            "token" to accessToken?.value,
            if (vodId == null) "fast_bread" to "true" else null
        )

        val url = buildUrl(baseUrl, *queryParams.toTypedArray()).toString()
        println("streamURLTag. url: $url")

        println("mylog: loadVideoUrl: $url")
        println("getPlaybackAccessToken: " + accessToken)
        return url
    }

    suspend fun getPlaybackUrlStream(channelName: String?): String {
        println("streamURLTag. in getPlaybackUrl")
        val supportedCodecs = "av1,h265,h264"
        val accessToken =
            gqlService.getPlaybackAccessToken(
                isLive = true,
                isVod = false,
                channelName = channelName,
                vodID = null,
                playerType = "site"
            ).data.streamPlaybackAccessToken
        println("streamURLTag. accessToken: $accessToken")
        val baseUrl =
            "https://usher.ttvnw.net/api/channel/hls/$channelName.m3u8"
        println("streamURLTag. baseUrl: $baseUrl")

        val queryParams = listOfNotNull(
            "allow_source" to "true",
            "allow_audio_only" to "true",
            "p" to Random.nextInt(9999999).toString(),
            "platform" to if (supportedCodecs.contains("av1", true)) "web" else null,
            "sig" to accessToken?.signature,
            "supported_codecs" to supportedCodecs,
            "token" to accessToken?.value,
            "fast_bread" to "true"
        )

        val url = buildUrl(baseUrl, *queryParams.toTypedArray()).toString()
        println("streamURLTag. url: $url")

        println("mylog: loadVideoUrl: $url")
        println("getPlaybackAccessToken: " + accessToken)
        return url
    }

    private fun buildUrl(url: String, vararg queryParams: Pair<String, String?>): Url {
        val builder = URLBuilder(url)
        for (pair in queryParams) {
            val (key, value) = pair
            builder.parameters.append(key, value ?: "")
        }
        return builder.build()
    }

    override fun saveToken(token: String) {
        TODO("Not yet implemented")
    }

    override fun loadToken() {
        TODO("Not yet implemented")
    }

    override suspend fun getFollowedChannels(): List<UserNode> {
        TODO("Not yet implemented")
    }

    override suspend fun followChannel(channel: Channel) {
        TODO("Not yet implemented")
    }

    override suspend fun unfollowChannel(id: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getChannelById(id: String): UserNode {
        TODO("Not yet implemented")
    }

    override suspend fun getWatchedList(): List<Watched_video> {
        TODO("Not yet implemented")
    }

    override suspend fun addToWatchedList(video: Watched_video, maxWatchedPosition: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun removeFromWatchedList(id: String) {
        TODO("Not yet implemented")
    }
}