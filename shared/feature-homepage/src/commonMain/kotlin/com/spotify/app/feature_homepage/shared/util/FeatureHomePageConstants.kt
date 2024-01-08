package com.spotify.app.feature_homepage.shared.util

import kotlin.time.Duration.Companion.hours

internal object FeatureHomePageConstants {

    val CACHE_EXPIRE_TIME = 6.hours

    internal object Endpoints {
        const val FETCH_FEATURED_PLAYLISTS = "v1/browse/featured-playlists"
        const val FETCH_FEATURED_ALBUMS = "v1/browse/new-releases"
    }
}