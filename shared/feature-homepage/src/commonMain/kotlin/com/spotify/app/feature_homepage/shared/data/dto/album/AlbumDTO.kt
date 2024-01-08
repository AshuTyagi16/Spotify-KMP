package com.spotify.app.feature_homepage.shared.data.dto.album

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AlbumDTO(
    @SerialName("items")
    val items: List<AlbumItemDTO>,

    @SerialName("limit")
    val limit: Int,

    @SerialName("total")
    val total: Int
)