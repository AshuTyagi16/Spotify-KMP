package com.spotify.app.feature_playlist_detail.shared.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlaylistDetailItemDTO(
    @SerialName("track")
    val track: TrackDTO
)