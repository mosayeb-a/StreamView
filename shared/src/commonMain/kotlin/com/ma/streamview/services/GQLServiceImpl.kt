package com.ma.streamview.services

import com.ma.streamview.GQL_CLIENT_ID
import com.ma.streamview.TWITCH_GQL_BASE_URL
import com.ma.streamview.data.model.GqlVideoTokenResponse
import com.ma.streamview.data.model.gql.GQLResponse
import com.ma.streamview.data.model.gql.user.UserVideosResponse
import com.ma.streamview.exceptionAwareRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.header
import io.ktor.client.request.setBody
import io.ktor.http.HttpMethod
import kotlinx.serialization.json.add
import kotlinx.serialization.json.addJsonObject
import kotlinx.serialization.json.buildJsonArray
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import kotlinx.serialization.json.putJsonArray
import kotlinx.serialization.json.putJsonObject

// todo : if you have auth restricted manifest exception: so use this :
//   RequestUri = new Uri($"https://twitch-downloader-proxy.twitcharchives.workers.dev/{videoId}.m3u8?sig={sig}&token={token}&allow_source=true&allow_audio_only=true&platform=web&player_backend=mediaplayer&playlist_include_framerate=true&supported_codecs=av1,h264"),
//                        Method = HttpMethod.Get

class GQLServiceImpl(private val httpClient: HttpClient) : GQLService {

    override suspend fun getTopStream(first: Int, after: Int?, tags: List<String>?): GQLResponse {
        return httpClient.exceptionAwareRequest<GQLResponse>(TWITCH_GQL_BASE_URL) {
            method = HttpMethod.Post
            header("Client-ID", GQL_CLIENT_ID)
            header("Content-Type", "application/json")
            setBody(
                buildJsonObject {
                    put("operationName", "TopStreams")
                    putJsonObject("variables") {
                        put("first", first)
                        put("after", after)
                        if (tags != null) {
                            put("tags", buildJsonArray {
                                tags.forEach { add(it) }
                            })
                        }
                    }
                    put(
                        "query", """
                    query TopStreams(${'$'}tags: [String!], ${'$'}first: Int, ${'$'}after: Cursor) {
                        streams(first: ${'$'}first, after: ${'$'}after, options: { freeformTags: ${'$'}tags } ) {
                            edges {
                                cursor
                                node {
                                    broadcaster {
                                        broadcastSettings {
                                            title
                                        }
                                        displayName
                                        id
                                        login
                                        profileImageURL(width: 300)
                                    }
                                    createdAt
                                    game {
                                        id
                                        displayName
                                        slug
                                    }
                                    id
                                    previewImageURL
                                    freeformTags {
                                        name
                                    }
                                    type
                                    viewersCount
                                }
                            }
                            pageInfo {
                                hasNextPage
                            }
                        }
                    }
                """.trimIndent()
                    )
                })
        }
    }

    override suspend fun searchStreams(query: String, first: Int, after: Int?): GQLResponse {
        return httpClient.exceptionAwareRequest<GQLResponse>(TWITCH_GQL_BASE_URL) {
            method = HttpMethod.Post
            header("Client-ID", GQL_CLIENT_ID)
            header("Content-Type", "application/json")
            setBody(
                buildJsonObject {
                    put("operationName", "SearchStreams")
                    putJsonObject("variables") {
                        put("query", query)
                        put("first", first)
                        put("after", after)
                    }
                    put(
                        "query", """
                    query SearchStreams(${'$'}query: String!, ${'$'}first: Int, ${'$'}after: Cursor) {
                        searchStreams(userQuery: ${'$'}query, first: ${'$'}first, after: ${'$'}after) {
                            edges {
                                cursor
                                node {
                                    broadcaster {
                                        broadcastSettings {
                                            title
                                        }
                                        displayName
                                        id
                                        login
                                        profileImageURL(width: 300)
                                    }
                                    createdAt
                                    game {
                                        id
                                        displayName
                                        slug
                                    }
                                    id
                                    previewImageURL
                                    freeformTags {
                                        name
                                    }
                                    type
                                    viewersCount
                                }
                            }
                            pageInfo {
                                hasNextPage
                            }
                        }
                    }
                """.trimIndent()
                    )
                }.toString()
            )
        }
    }


    override suspend fun searchChannels(query: String, first: Int, after: Int?): GQLResponse {
        return httpClient.exceptionAwareRequest<GQLResponse>(TWITCH_GQL_BASE_URL) {
            method = HttpMethod.Post
            header("Client-ID", GQL_CLIENT_ID)
            header("Content-Type", "application/json")
            setBody(
                buildJsonObject {
                    put("operationName", "SearchChannels")
                    putJsonObject("variables") {
                        put("query", query)
                        put("first", first)
                        put("after", after)
                    }
                    put(
                        "query", """
                    query SearchChannels(${'$'}query: String!, ${'$'}first: Int, ${'$'}after: Cursor) {
                        searchUsers(userQuery: ${'$'}query, first: ${'$'}first, after: ${'$'}after) {
                            edges {
                                cursor
                                node {
                                    displayName
                                    followers {
                                        totalCount
                                    }
                                    id
                                    login
                                    profileImageURL(width: 300)
                                    stream {
                                        type
                                    }
                                }
                            }
                            pageInfo {
                                hasNextPage
                            }
                        }
                    }
                """.trimIndent()
                    )
                }.toString()
            )
        }
    }

    override suspend fun searchCategories(
        query: String,
        first: Int,
        after: Int?
    ): GQLResponse {
        return httpClient.exceptionAwareRequest<GQLResponse>(TWITCH_GQL_BASE_URL) {
            method = HttpMethod.Post
            header("Content-Type", "application/json")
            header("Client-Id", GQL_CLIENT_ID)
            setBody(
                buildJsonObject {
                    put("operationName", "SearchGames")
                    putJsonObject("variables") {
                        put("query", query)
                        put("first", first)
                        put("after", after)
                    }
                    put(
                        "query", """
                    query SearchGames(${'$'}query: String!, ${'$'}first: Int, ${'$'}after: Cursor) {
                        searchCategories(query: ${'$'}query, first: ${'$'}first, after: ${'$'}after) {
                            edges {
                                cursor
                                node {
                                    boxArtURL
                                    broadcastersCount
                                    displayName
                                    id
                                    slug
                                    tags(tagType: CONTENT) {
                                        id
                                        localizedName
                                    }
                                    viewersCount
                                }
                            }
                            pageInfo {
                                hasNextPage
                            }
                        }
                    }
                """.trimIndent()
                    )
                }
            )
        }
    }

    override suspend fun getStreamsByGameId(
        id: String,
        sort: String,
        tags: List<String>?,
        first: Int,
        after: Int?
    ): GQLResponse {
        return httpClient.exceptionAwareRequest<GQLResponse>(TWITCH_GQL_BASE_URL) {
            method = HttpMethod.Post
            header("Content-Type", "application/json")
            header("Client-Id", GQL_CLIENT_ID)
            setBody(
                buildJsonObject {
                    put("operationName", "GetStreamsByGameId")
                    putJsonObject("variables") {
                        put("id", id)
                        put("sort", sort)
                        if (tags != null) {
                            put("tags", buildJsonArray {
                                tags.forEach { add(it) }
                            })
                        }
                        put("first", first)
                        put("after", after)
                    }
                    put(
                        "query", """
                    query GetStreamsByGameId(${'$'}id: String!, ${'$'}sort: String!, ${'$'}tags: [String!], ${'$'}first: Int, ${'$'}after: Cursor) {
                        streams(gameID: ${'$'}id, sort: ${'$'}sort, tags: ${'$'}tags, first: ${'$'}first, after: ${'$'}after) {
                            edges {
                                cursor
                                node {
                                    id
                                    title
                                    viewerCount
                                    streamTags {
                                        id
                                        localizedName
                                    }
                                }
                            }
                            pageInfo {
                                hasNextPage
                            }
                        }
                    }
                """.trimIndent()
                    )
                }
            )
        }
    }


    override suspend fun getVideosByGameId(
        id: String,
        sort: String,
        tags: List<String>?,
        first: Int,
        after: Int?
    ): GQLResponse {
        return httpClient.exceptionAwareRequest<GQLResponse>(TWITCH_GQL_BASE_URL) {
            method = HttpMethod.Post
            header("Content-Type", "application/json")
            header("Client-Id", GQL_CLIENT_ID)
            setBody(
                buildJsonObject {
                    put("operationName", "GetVideosByGameId")
                    putJsonObject("variables") {
                        put("id", id)
                        put("sort", sort)
                        if (tags != null) {
                            put("tags", buildJsonArray {
                                tags.forEach { add(it) }
                            })
                        }
                        put("first", first)
                        put("after", after)
                    }
                    put(
                        "query", """
                    query GetVideosByGameId(${'$'}id: String!, ${'$'}sort: String!, ${'$'}tags: [String!], ${'$'}first: Int, ${'$'}after: Cursor) {
                        videos(gameID: ${'$'}id, sort: ${'$'}sort, tags: ${'$'}tags, first: ${'$'}first, after: ${'$'}after) {
                            edges {
                                cursor
                                node {
                                    id
                                    title
                                    viewCount
                                    videoTags {
                                        id
                                        localizedName
                                    }
                                }
                            }
                            pageInfo {
                                hasNextPage
                            }
                        }
                    }
                """.trimIndent()
                    )
                }
            )
        }
    }


    override suspend fun getTopCategories(
        first: Int,
        after: Int?,
        tags: List<String>?
    ): GQLResponse {
        return httpClient.exceptionAwareRequest<GQLResponse>(TWITCH_GQL_BASE_URL) {
            method = HttpMethod.Post
            header("Content-Type", "application/json")
            header("Client-Id", GQL_CLIENT_ID)
            setBody(
                buildJsonObject {
                    put("operationName", "GetTopCategories")
                    putJsonObject("variables") {
                        put("first", 30)
                        put("after", after)
                        if (tags != null) {
                            put("tags", buildJsonArray {
                                tags.forEach { add(it) }
                            })
                        }
                    }
                    put(
                        "query", """
                        query GetTopCategories(${'$'}tags: [String!], ${'$'}first: Int, ${'$'}after: Cursor) {
                            games(first: ${'$'}first, after: ${'$'}after, options: { tags: ${'$'}tags } ) {
                                edges {
                                    cursor
                                    node {
                                        boxArtURL
                                        broadcastersCount
                                        displayName
                                        id
                                        slug
                                        tags(tagType: CONTENT) {
                                            id
                                            localizedName
                                        }
                                        viewersCount
                                    }
                                }
                                pageInfo {
                                    hasNextPage
                                }
                            }
                        }
                    """.trimIndent()
                    )
                }
            )
        }
    }


    override suspend fun getUserStreams(userIds: List<String>): GQLResponse {
        return httpClient.exceptionAwareRequest<GQLResponse>(TWITCH_GQL_BASE_URL) {
            method = HttpMethod.Post
            header("Content-Type", "application/json")
//            header(
//                "X-APOLLO-OPERATION-ID",
//                "d99f8942a21780e4e89a218e7f8a1099c8207655490d192e1187b1f07aa48f61"
//            )
//            header("X-APOLLO-OPERATION-NAME", "UsersStream")
//            header("Accept", "multipart/mixed; deferSpec=20220824, application/json")
            header("Client-Id", "ue6666qo983tsx6so1t0vnawi233wa")
            setBody(
                buildJsonObject {
                    put("operationName", "UsersStream")
                    putJsonObject("variables") {
                        putJsonArray("id") {
                            userIds.forEach { add(it) }
                        }
                    }
                    put(
                        "query", """
                query UsersStream(${'$'}id: [ID!], ${'$'}login: [String!]) {
                    users(ids: ${'$'}id, logins: ${'$'}login) {
                        displayName
                        id
                        login
                        profileImageURL(width: 300)
                        stream {
                            broadcaster {
                                broadcastSettings {
                                    title
                                }
                            }
                            createdAt
                            game {
                                id
                                displayName
                                slug
                            }
                            id
                            previewImageURL
                            freeformTags {
                                name
                            }
                            type
                            viewersCount
                        }
                    }
                }
            """.trimIndent()
                    )
                }
            )
        }
    }


    override suspend fun getUser(login: String): GQLResponse {
        return httpClient.exceptionAwareRequest<GQLResponse>(TWITCH_GQL_BASE_URL) {
            method = HttpMethod.Post
            header("Content-Type", "application/json")
            header(
                "X-APOLLO-OPERATION-ID",
                "40f6f64b064d6920af40a62af9133248bb083fabaf63349437c15f9479d64633"
            )
            header("X-APOLLO-OPERATION-NAME", "UserChannelPage")
            header("Accept", "multipart/mixed; deferSpec=20220824, application/json")
            header("Client-Id", "ue6666qo983tsx6so1t0vnawi233wa")
            setBody(
                buildJsonObject {
                    put("operationName", "UserChannelPage")
                    putJsonObject("variables") {
                        put("id", login)
                    }
                    put(
                        "query", """
                query UserChannelPage(${'$'}id: ID, ${'$'}login: String) {
                    user(id: ${'$'}id, login: ${'$'}login, lookupType: ALL) {
                        bannerImageURL
                        createdAt
                        displayName
                        followers {
                            totalCount
                        }
                        lastBroadcast {
                            startedAt
                        }
                        id
                        login
                        profileImageURL(width: 300)
                        roles {
                            isAffiliate
                            isGlobalMod
                            isPartner
                            isSiteAdmin
                            isStaff
                        }
                        stream {
                            createdAt
                            game {
                                id
                                displayName
                                slug
                            }
                            id
                            title
                            type
                            viewersCount
                        }
                    }
                }
            """.trimIndent()
                    )
                }
            )
        }
    }

    override suspend fun getUserVideos(
        userId: String,
        first: Int,
        after: Int?,
        tags: List<String>?,
        sort: String
    ): UserVideosResponse {
        return httpClient.exceptionAwareRequest<UserVideosResponse>(TWITCH_GQL_BASE_URL) {
            method = HttpMethod.Post
            header("Content-Type", "application/json")
            header(
                "X-APOLLO-OPERATION-ID",
                "3156559d2f6c3e791a42854b8b6ed7b8bc5a8dceb0829fdc00aee731f6842158"
            )
            header("X-APOLLO-OPERATION-NAME", "UserVideos")
            header("Accept", "multipart/mixed; deferSpec=20220824, application/json")
            header("Client-Id", "ue6666qo983tsx6so1t0vnawi233wa")
            setBody(
                buildJsonObject {
                    put("operationName", "UserVideos")
                    putJsonObject("variables") {
                        put("id", userId)
                        put("sort", sort)
                        tags?.let { buildJsonArray { it.forEach { add(it) } } }
                            ?.let { put("types", it) }
                        put("first", first)
                        put("after", after)
                    }
                    put(
                        "query", """
                query UserVideos(${'$'}id: ID, ${'$'}login: String, ${'$'}sort: VideoSort, ${'$'}types: [BroadcastType!], ${'$'}first: Int, ${'$'}after: Cursor) {
                    user(id: ${'$'}id, login: ${'$'}login, lookupType: ALL) {
                        displayName
                        login
                        profileImageURL(width: 300)
                        videos(first: ${'$'}first, after: ${'$'}after, types: ${'$'}types, sort: ${'$'}sort) {
                            edges {
                                cursor
                                node {
                                    animatedPreviewURL
                                    broadcastType
                                    contentTags {
                                        id
                                        localizedName
                                    }
                                    createdAt
                                    game {
                                        id
                                        displayName
                                        slug
                                    }
                                    id
                                    lengthSeconds
                                    previewThumbnailURL
                                    title
                                    viewCount
                                }
                            }
                            pageInfo {
                                hasNextPage
                            }
                        }
                    }
                }
            """.trimIndent()
                    )
                }
            )
        }
    }


    override suspend fun searchVideos(cursor: String, query: String): GQLResponse {
        return httpClient.exceptionAwareRequest<GQLResponse>(TWITCH_GQL_BASE_URL) {
            method = HttpMethod.Post
            header("Client-ID", GQL_CLIENT_ID)
            header("Content-Type", "application/json")
            setBody(
                buildJsonObject {
                    put("operationName", "SearchVideos")
                    putJsonObject("variables") {
                        put("query", query)
                        put("first", 30)
                        put("after", cursor)
                    }
                    put(
                        "query", """
                    query SearchVideos(${'$'}query: String!, ${'$'}first: Int, ${'$'}after: String) {
                        searchFor(userQuery: ${'$'}query, platform: "", target: { cursor: ${'$'}after index: VOD limit: ${'$'}first }) {
                            videos {
                                cursor
                                items {
                                    animatedPreviewURL
                                    broadcastType
                                    contentTags {
                                        id
                                        localizedName
                                    }
                                    createdAt
                                    game {
                                        displayName
                                        id
                                        slug
                                    }
                                    id
                                    lengthSeconds
                                    owner {
                                        displayName
                                        id
                                        login
                                        profileImageURL(width: 300)
                                    }
                                    previewThumbnailURL
                                    title
                                    viewCount
                                }
                                pageInfo {
                                    hasNextPage
                                }
                            }
                        }
                    }
                """.trimIndent()
                    )
                }.toString()
            )
        }
    }


    override suspend fun getPlaybackAccessToken(
        isLive: Boolean,
        isVod: Boolean,
        channelName: String?,
        vodID: String,
        playerType: String
    ): GqlVideoTokenResponse {
        return httpClient.exceptionAwareRequest<GqlVideoTokenResponse>(TWITCH_GQL_BASE_URL) {
            method = HttpMethod.Post
            header("Content-Type", "application/json")
            header("Client-Id", GQL_CLIENT_ID)
            setBody(
                buildJsonObject {
                    putJsonObject("extensions") {
                        putJsonObject("persistedQuery") {
                            put(
                                "sha256Hash",
                                "3093517e37e4f4cb48906155bcd894150aef92617939236d2508f3375ab732ce"
                            )
                            put("version", 1)
                        }
                    }
                    put("operationName", "PlaybackAccessToken")
                    putJsonObject("variables") {
                        put("isLive", isLive)
                        put("login", channelName ?: "")
                        put("isVod", isVod)
                        put("vodID", vodID)
                        put("playerType", playerType)
                    }
                }
            )
        }
    }
}

//    override suspend fun getUsers(ids: Set<String?>): Users {
//        return httpClient.exceptionAwareRequest<Users>(TWITCH_HELIX_BASE_URL + "users") {
//            method = HttpMethod.Get
//
////            parameter("id", ids.joinToString(","))
////            parameter("id", ids)
//            ids.forEach { id ->
//                parameter("id", id)
//            }
//            header("Client-Id", CLIENT_ID)
//            header("Authorization", "Bearer ${TokenContainer.token}")
//        }
//    }

//    override suspend fun getCategories(): CategoiresResponse {
//        return httpClient.exceptionAwareRequest<CategoiresResponse>(TWITCH_HELIX_BASE_URL + "games/top") {
//            method = HttpMethod.Get
////            timeout {
////                requestTimeoutMillis = 60000
////                connectTimeoutMillis = 60000
////                socketTimeoutMillis = 60000
////            }
//            header("Client-Id", CLIENT_ID)
//            header("Authorization", "Bearer ${TokenContainer.token}")
//        }
//    }

//  override suspend fun getVideosById(
//        userId: String?,
//        id: String?,
//        gameId: String?
//    ): VideoResponse3 {
//        return httpClient.exceptionAwareRequest<VideoResponse3>(TWITCH_HELIX_BASE_URL + "videos") {
//            method = HttpMethod.Get
//            header("Client-Id", CLIENT_ID)
//            header("Authorization", "Bearer ${TokenContainer.token}")
//            if (userId != null) {
//                parameter("user_id", userId)
//            }
//            if (gameId != null) {
//                parameter("game_id", gameId)
//            }
//            if (id != null) {
//                parameter("id", id)
//            }
//
//        }
//    }