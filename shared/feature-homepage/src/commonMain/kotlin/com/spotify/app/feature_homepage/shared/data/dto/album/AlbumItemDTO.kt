package com.spotify.app.feature_homepage.shared.data.dto.album

import com.spotify.app.core_base.shared.data.dto.ImageDTO
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AlbumItemDTO(
    @SerialName("album_type")
    val albumType: String,

    @SerialName("artists")
    val artists: List<ArtistDTO>,

    @SerialName("id")
    val id: String,

    @SerialName("images")
    val images: List<ImageDTO>,

    @SerialName("name")
    val name: String,

    @SerialName("release_date")
    val releaseDate: String,

    @SerialName("release_date_precision")
    val releaseDatePrecision: String,

    @SerialName("total_tracks")
    val totalTracks: Int,

    @SerialName("type")
    val type: String,

    @SerialName("uri")
    val uri: String
)