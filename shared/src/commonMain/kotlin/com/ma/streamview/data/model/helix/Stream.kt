package com.ma.streamview.data.model.helix


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Stream(
    @SerialName("game_id")
    val gameId: String,
    @SerialName("game_name")
    val gameName: String,
    @SerialName("id")
    val id: String,
    @SerialName("is_mature")
    val isMature: Boolean,
    @SerialName("language")
    val language: String,
    @SerialName("started_at")
    val startedAt: String,
    @SerialName("tag_ids")
    val tagIds: List<String>,
    @SerialName("tags")
    val tags: List<String>,
    @SerialName("thumbnail_url")
    val thumbnailUrl: String,
    @SerialName("title")
    val title: String,
    @SerialName("type")
    val type: String,
    @SerialName("user_id")
    val userId: String,
    @SerialName("user_login")
    val userLogin: String,
    @SerialName("user_name")
    val userName: String,
    @SerialName("viewer_count")
    val viewerCount: Int
)