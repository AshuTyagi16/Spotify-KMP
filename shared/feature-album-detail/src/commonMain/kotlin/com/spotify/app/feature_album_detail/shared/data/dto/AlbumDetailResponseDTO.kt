package com.spotify.app.feature_album_detail.shared.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AlbumDetailResponseDTO(
    @SerialName("items")
    val items: List<AlbumDetailItemDTO>,

    @SerialName("limit")
    val limit: Long,

    @SerialName("offset")
    val offset: Long,

    @SerialName("total")
    val total: Long
)