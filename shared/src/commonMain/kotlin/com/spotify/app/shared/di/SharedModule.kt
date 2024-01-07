package com.spotify.app.shared.di

import com.spotify.app.core_logger.shared.impl.di.loggerModule
import com.spotify.app.core_network.shared.impl.di.networkModule
import com.spotify.app.core_preferences.shared.impl.di.preferencesModule
import com.spotify.app.feature_homepage.shared.di.featureHomePageModule

fun getSharedModules() =
    loggerModule + preferencesModule + networkModule + featureHomePageModule