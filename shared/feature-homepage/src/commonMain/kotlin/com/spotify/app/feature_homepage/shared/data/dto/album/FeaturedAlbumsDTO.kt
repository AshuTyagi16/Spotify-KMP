package com.spotify.app.feature_homepage.shared.data.dto.album

import com.spotify.app.core_base.shared.data.dto.AlbumDTO
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FeaturedAlbumsDTO(
    @SerialName("albums")
    val albums: AlbumDTO
)