package com.spotify.app.shared

import com.spotify.app.core_logger.shared.api.LoggerApi
import com.spotify.app.core_network.shared.api.HttpClientApi
import com.spotify.app.core_preferences.shared.api.PreferenceUtilApi
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
class SharedModuleHelper : KoinComponent {

    val httpClientApi by inject<HttpClientApi>()

    val loggerApi by inject<LoggerApi>()

    val preferencesUtilApi by inject<PreferenceUtilApi>()
}