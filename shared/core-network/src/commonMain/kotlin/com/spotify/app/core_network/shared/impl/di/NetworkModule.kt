package com.spotify.app.core_network.shared.impl.di

import com.spotify.app.core_network.shared.CoreNetworkBuildKonfig
import com.spotify.app.core_network.shared.impl.HttpClientApiImpl
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val networkModule = module {
    single {
        HttpClientApiImpl(
            shouldEnableLogging = CoreNetworkBuildKonfig.DEBUG,
            json = get(),
            loggerApi = get(),
            preferenceUtilApi = get()
        )
    }
    single {
        Json {
            ignoreUnknownKeys = true
            prettyPrint = false
            isLenient = true
            useAlternativeNames = true
            encodeDefaults = true
            explicitNulls = false
        }
    }
}