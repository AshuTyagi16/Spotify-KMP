package com.spotify.app.core_base.shared.domain.model

data class AlbumItem(
    val id: String,
    val name: String,
    val image: String,
    val releaseDate: String?,
    val trackCount: Long?,
    val artists: String
)