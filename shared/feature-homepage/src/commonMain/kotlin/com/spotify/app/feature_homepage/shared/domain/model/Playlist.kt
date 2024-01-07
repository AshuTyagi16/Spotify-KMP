package com.spotify.app.feature_homepage.shared.domain.model

data class Playlist(
    val items: List<PlaylistItem>,

    val total: Int
)