package com.ma.streamview.services

import com.ma.streamview.data.model.TokenResponse
import com.ma.streamview.data.model.helix.StreamResponse
import com.ma.streamview.data.model.helix.VideoResponse

interface HelixService {
    suspend fun getAccessToken(): TokenResponse
    suspend fun getVideos(ids: List<String>): VideoResponse
    suspend fun getStreams(userIds: List<String>): StreamResponse
}