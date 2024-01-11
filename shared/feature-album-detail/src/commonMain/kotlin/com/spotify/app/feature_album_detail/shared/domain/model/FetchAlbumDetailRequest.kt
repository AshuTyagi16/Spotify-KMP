package com.spotify.app.feature_album_detail.shared.domain.model

data class FetchAlbumDetailRequest(
    val albumId: String,
    val limit: Long,
    val offset: Long
)