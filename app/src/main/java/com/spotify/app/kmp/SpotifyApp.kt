package com.spotify.app.kmp

import android.app.Application
import com.spotify.app.core_network.shared.impl.HttpEngineProviderAndroid
import com.spotify.app.shared.di.getSharedModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class SpotifyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@SpotifyApp)
            androidLogger()
            modules(getSharedModules(httpEngineProvider = HttpEngineProviderAndroid()))
        }
    }

}