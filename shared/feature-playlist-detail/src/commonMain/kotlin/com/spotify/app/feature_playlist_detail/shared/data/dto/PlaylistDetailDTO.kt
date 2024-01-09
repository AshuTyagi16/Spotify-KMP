package com.spotify.app.feature_playlist_detail.shared.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlaylistDetailDTO(
    @SerialName("items")
    val items: List<PlaylistDetailItemDTO>,

    @SerialName("limit")
    val limit: Int,

    @SerialName("offset")
    val offset: Int,

    @SerialName("total")
    val total: Int
)