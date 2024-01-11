package com.spotify.app.feature_album_detail.shared.data.dto

import com.spotify.app.core_base.shared.data.dto.ArtistDTO
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AlbumDetailItemDTO(

    @SerialName("artists")
    val artists: List<ArtistDTO>,

    @SerialName("duration_ms")
    val durationMs: Int,

    @SerialName("id")
    val id: String,

    @SerialName("name")
    val name: String,

    @SerialName("track_number")
    val trackNumber: Int,
)