package com.spotify.app.feature_homepage.shared.util

internal object FeatureHomePageConstants {

    const val QUALIFIER_HOMEPAGE_DATABASE = "QUALIFIER_HOMEPAGE_DATABASE"
    const val HOMEPAGE_DATABASE_NAME = "homepage.db"

    internal object Endpoints {
        const val FETCH_FEATURED_PLAYLISTS = "v1/browse/featured-playlists"
        const val FETCH_FEATURED_ALBUMS = "v1/browse/new-releases"
    }
}