package com.spotify.app.shared

import com.spotify.app.core_network.shared.impl.HttpEngineProviderIOS
import com.spotify.app.shared.di.getSharedModules
import org.koin.core.context.startKoin

fun initKoin() {
    startKoin {
        modules(getSharedModules(httpEngineProvider = HttpEngineProviderIOS()))
    }
}