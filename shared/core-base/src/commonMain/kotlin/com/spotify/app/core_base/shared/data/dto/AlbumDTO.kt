package com.spotify.app.core_base.shared.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AlbumDTO(
    @SerialName("items")
    val items: List<AlbumItemDTO>? = null,

    @SerialName("limit")
    val limit: Int? = null,

    @SerialName("total")
    val total: Int? = null
)