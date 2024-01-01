package com.spotify.app.core_network.shared.api

import com.spotify.app.core_network.shared.impl.HttpEngineProvider
import io.ktor.client.HttpClient


interface HttpClientApi {
    fun getHttpClient(httpEngineProvider: HttpEngineProvider): HttpClient

}