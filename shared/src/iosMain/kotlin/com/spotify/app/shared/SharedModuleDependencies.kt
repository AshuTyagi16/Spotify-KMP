package com.spotify.app.shared

import com.spotify.app.core_logger.shared.api.LoggerApi
import com.spotify.app.core_network.shared.api.HttpClientApi
import com.spotify.app.core_preferences.shared.api.PreferenceUtilApi
import com.spotify.app.feature_album_detail.shared.ui.AlbumDetailViewModel
import com.spotify.app.feature_homepage.shared.ui.HomePageViewModel
import com.spotify.app.feature_playlist_detail.shared.ui.PlaylistDetailViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object SharedModuleDependencies : KoinComponent {

    val httpClientApi by inject<HttpClientApi>()

    val loggerApi by inject<LoggerApi>()

    val preferencesUtilApi by inject<PreferenceUtilApi>()

    val homePageViewModel by inject<HomePageViewModel>()

    val playlistDetailViewModel by inject<PlaylistDetailViewModel>()

    val albumDetailViewModel by inject<AlbumDetailViewModel>()

}