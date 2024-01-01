package com.spotify.app.core_network.shared.impl

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp

class HttpEngineProviderAndroid : HttpEngineProvider {

    override fun clientEngine(shouldEnableLogging: Boolean): HttpClientEngine {
        return OkHttp.create()
    }

}