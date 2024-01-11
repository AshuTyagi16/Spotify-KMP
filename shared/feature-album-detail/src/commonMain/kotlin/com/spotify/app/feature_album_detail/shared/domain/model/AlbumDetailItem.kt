package com.spotify.app.feature_album_detail.shared.domain.model

data class AlbumDetailItem(
    val id: String,
    val albumId: String,
    val trackName: String,
    val artists: String,
    val limitValue: Long,
    val offsetValue: Long,
    val total: Long,
)
