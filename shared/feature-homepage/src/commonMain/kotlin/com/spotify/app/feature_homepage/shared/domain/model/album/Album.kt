package com.spotify.app.feature_homepage.shared.domain.model.album

data class Album(
    val items: List<AlbumItem>,
    val limit: Int,
    val total: Int
)