package com.spotify.app.core_network.shared.impl.di

import com.spotify.app.core_network.shared.CoreNetworkBuildKonfig
import com.spotify.app.core_network.shared.impl.HttpClientApiImpl
import com.spotify.app.core_network.shared.impl.HttpEngineProvider
import org.koin.dsl.module

fun createNetworkModule(httpEngineProvider: HttpEngineProvider) = module {
    single {
        HttpClientApiImpl(
            shouldEnableLogging = CoreNetworkBuildKonfig.DEBUG,
            httpEngineProvider = httpEngineProvider,
            loggerApi = get(),
            preferenceUtilApi = get()
        )
    }
}