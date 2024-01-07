package com.spotify.app.core_base.shared.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImageDTO(
    @SerialName("url")
    val url: String
)