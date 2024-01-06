package com.spotify.app.core_network.shared.impl.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenRequest(
    @SerialName("grant_type")
    val grantType: String,

    @SerialName("client_secret")
    val clientSecret: String,

    @SerialName("client_id")
    val clientId: String
)