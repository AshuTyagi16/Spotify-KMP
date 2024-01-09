package com.spotify.app.feature_homepage.shared.domain.model.album

data class AlbumItem(
    val id: String,
    val name: String,
    val image: String,
    val releaseDate: String,
    val trackCount: Long,
    val artists: String
)