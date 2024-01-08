package com.spotify.app.feature_homepage.shared.data.dto.album

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArtistDTO(
    @SerialName("id")
    val id: String,

    @SerialName("name")
    val name: String,

    @SerialName("type")
    val type: String,

    @SerialName("uri")
    val uri: String
)