package com.spotify.app.core_network.shared.impl.exception

data class RestClientException(
    val errorMessage: String
) : Exception(errorMessage)