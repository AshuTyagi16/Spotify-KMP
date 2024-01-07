package com.spotify.app.feature_homepage.shared.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlaylistDTO(
    @SerialName("items")
    val items: List<PlaylistItemDTO>,

    @SerialName("total")
    val total: Int
)