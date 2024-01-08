package com.spotify.app.feature_homepage.shared.data.dto.playlist

import com.spotify.app.core_base.shared.data.dto.ImageDTO
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlaylistItemDTO(
    @SerialName("description")
    val description: String,

    @SerialName("id")
    val id: String,

    @SerialName("images")
    val images: List<ImageDTO>,

    @SerialName("name")
    val name: String,

    @SerialName("tracks")
    val tracks: TracksInfoDto
)