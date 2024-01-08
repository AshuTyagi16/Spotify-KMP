package com.spotify.app.feature_homepage.shared.domain.model.playlist

data class Playlist(
    val items: List<PlaylistItem>,

    val total: Int
)