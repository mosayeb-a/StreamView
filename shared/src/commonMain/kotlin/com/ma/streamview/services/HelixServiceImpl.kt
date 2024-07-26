package com.ma.streamview.services

import com.ma.streamview.CLIENT_ID
import com.ma.streamview.CLIENT_SECRET
import com.ma.streamview.TWITCH_HELIX_BASE_URL
import com.ma.streamview.TWITCH_OAUTH_URL
import com.ma.streamview.data.model.TokenContainer
import com.ma.streamview.data.model.TokenResponse
import com.ma.streamview.data.model.helix.StreamResponse
import com.ma.streamview.data.model.helix.VideoResponse
import com.ma.streamview.exceptionAwareRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.setBody
import io.ktor.http.HttpMethod
import io.ktor.http.Parameters

class HelixServiceImpl(private val httpClient: HttpClient) : HelixService {

    override suspend fun getAccessToken(): TokenResponse {
        return httpClient.exceptionAwareRequest<TokenResponse>(TWITCH_OAUTH_URL + "token") {
            method = HttpMethod.Post
            header("Content-Type", "application/x-www-form-urlencoded")
            setBody(
                FormDataContent(
                    Parameters.build {
                        append("client_id", CLIENT_ID)
                        append("client_secret", CLIENT_SECRET)
                        append("grant_type", "client_credentials")
                    })
            )
        }
    }

    override suspend fun getVideos(ids: List<String>): VideoResponse {
        return httpClient.exceptionAwareRequest<VideoResponse>(TWITCH_HELIX_BASE_URL + "videos") {
            println("helixRequest. in request: token: ${TokenContainer.token} ")
            header("Authorization", "Bearer ${TokenContainer.token}")
            header("Client-Id", CLIENT_ID)
            ids.forEach { id ->
                parameter("id", id)
            }
        }
    }

    override suspend fun getStreams(userIds: List<String>): StreamResponse {
        return httpClient.exceptionAwareRequest<StreamResponse>(TWITCH_HELIX_BASE_URL + "streams") {
            println("helixRequest. token: ${TokenContainer.token}")
            header("Authorization", "Bearer ${TokenContainer.token}")
            header("Client-Id", CLIENT_ID)
            userIds.forEach { id ->
                parameter("user_id", id)
            }
        }
    }
}