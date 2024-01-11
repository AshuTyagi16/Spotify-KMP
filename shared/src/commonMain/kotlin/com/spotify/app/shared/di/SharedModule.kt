package com.spotify.app.shared.di

import com.spotify.app.core_logger.shared.impl.di.loggerModule
import com.spotify.app.core_network.shared.impl.di.networkModule
import com.spotify.app.core_preferences.shared.impl.di.preferencesModule
import com.spotify.app.feature_album_detail.shared.di.featureAlbumDetailModule
import com.spotify.app.feature_album_detail.shared.di.getFeatureAlbumDetailPlatformModule
import com.spotify.app.feature_homepage.shared.di.featureHomePageModule
import com.spotify.app.feature_homepage.shared.di.getHomePlatformModule
import com.spotify.app.feature_playlist_detail.shared.di.featurePlaylistDetailModule
import com.spotify.app.feature_playlist_detail.shared.di.getFeaturePlaylistDetailPlatformModule

fun getSharedModules() =
    loggerModule +
            preferencesModule +
            networkModule +
            featureHomePageModule + getHomePlatformModule() +
            featurePlaylistDetailModule + getFeaturePlaylistDetailPlatformModule() +
            featureAlbumDetailModule + getFeatureAlbumDetailPlatformModule()
