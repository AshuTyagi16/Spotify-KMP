package com.spotify.app.feature_playlist_detail.shared.domain.model

data class PlaylistDetail(
    val items: List<PlaylistDetailItem>,
    val limit: Int,
    val offset: Int,
    val total: Int
)