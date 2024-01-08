package com.spotify.app.feature_homepage.shared.data.dto.playlist

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FeaturedPlaylistsDTO(
    @SerialName("message")
    val message: String,

    @SerialName("playlists")
    val playlists: PlaylistDTO
)