package com.spotify.app.shared.di

import com.spotify.app.core_logger.shared.impl.di.loggerModule
import com.spotify.app.core_network.shared.impl.HttpEngineProvider
import com.spotify.app.core_network.shared.impl.di.createNetworkModule
import com.spotify.app.core_preferences.shared.impl.di.preferencesModule

fun getSharedModules(httpEngineProvider: HttpEngineProvider) =
    loggerModule + preferencesModule + createNetworkModule(httpEngineProvider = httpEngineProvider)