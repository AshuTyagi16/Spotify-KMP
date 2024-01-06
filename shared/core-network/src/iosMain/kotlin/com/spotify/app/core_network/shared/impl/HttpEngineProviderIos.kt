package com.spotify.app.core_network.shared.impl

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin

actual class HttpEngineProvider {
    actual fun clientEngine(shouldEnableLogging: Boolean): HttpClientEngine = Darwin.create()

}