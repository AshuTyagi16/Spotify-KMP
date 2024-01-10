package com.spotify.app.feature_playlist_detail.shared.domain.model

data class PlaylistDetail(
    val items: List<PlaylistDetailItem>,
    val limit: Long,
    val offset: Long,
    val total: Long
)