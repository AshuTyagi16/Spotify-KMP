package com.spotify.app.core_network.shared.impl.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenRequest(
    @SerialName("grant_type")
    val grantType: String,

    @SerialName("refresh_token")
    val refreshToken: String,

    @SerialName("client_id")
    val clientId: String
)