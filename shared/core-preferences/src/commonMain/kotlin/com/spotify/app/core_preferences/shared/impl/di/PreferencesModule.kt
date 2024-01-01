package com.spotify.app.core_preferences.shared.impl.di

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.Settings
import com.russhwolf.settings.coroutines.toSuspendSettings
import com.spotify.app.core_preferences.shared.api.PreferenceApi
import com.spotify.app.core_preferences.shared.api.PreferenceUtilApi
import com.spotify.app.core_preferences.shared.impl.PreferenceApiImpl
import com.spotify.app.core_preferences.shared.impl.util.PreferenceUtilImpl
import org.koin.dsl.module

@OptIn(ExperimentalSettingsApi::class)
val preferencesModule = module {
    single<PreferenceApi> {
        PreferenceApiImpl(suspendSettings = Settings().toSuspendSettings())
    }
    single<PreferenceUtilApi> {
        PreferenceUtilImpl(get())
    }
}