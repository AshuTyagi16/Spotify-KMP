package com.spotify.app.core_base.shared.domain.model

data class Album(
    val items: List<AlbumItem>?,
    val limit: Int?,
    val total: Int?
)