package com.spotify.app.core_base.shared.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AlbumItemDTO(

    @SerialName("artists")
    val artists: List<ArtistDTO>,

    @SerialName("id")
    val id: String,

    @SerialName("images")
    val images: List<ImageDTO>? = null,

    @SerialName("name")
    val name: String,

    @SerialName("release_date")
    val releaseDate: String? = null,

    @SerialName("total_tracks")
    val totalTracks: Long? = null
)