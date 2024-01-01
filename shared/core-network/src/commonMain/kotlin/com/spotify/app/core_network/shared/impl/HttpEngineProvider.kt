package com.spotify.app.core_network.shared.impl

import io.ktor.client.engine.HttpClientEngine

interface HttpEngineProvider {
    fun clientEngine(shouldEnableLogging: Boolean): HttpClientEngine
}