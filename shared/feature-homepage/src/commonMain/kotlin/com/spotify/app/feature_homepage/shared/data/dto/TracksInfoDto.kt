package com.spotify.app.feature_homepage.shared.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TracksInfoDto(
    @SerialName("total")
    val total: Int
)