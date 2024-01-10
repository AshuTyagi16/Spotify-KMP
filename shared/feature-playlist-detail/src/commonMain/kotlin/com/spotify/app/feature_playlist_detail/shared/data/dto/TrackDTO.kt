package com.spotify.app.feature_playlist_detail.shared.data.dto

import com.spotify.app.core_base.shared.data.dto.AlbumItemDTO
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TrackDTO(

    @SerialName("id")
    val id: String,

    @SerialName("name")
    val name: String,

    @SerialName("popularity")
    val popularity: Int,

    @SerialName("preview_url")
    val previewUrl: String? = null,

    @SerialName("track")
    val track: Boolean,

    @SerialName("duration_ms")
    val durationMs: Int,

    @SerialName("episode")
    val episode: Boolean,

    @SerialName("explicit")
    val explicit: Boolean,

    @SerialName("track_number")
    val trackNumber: Int,

    @SerialName("album")
    val album: AlbumItemDTO
)