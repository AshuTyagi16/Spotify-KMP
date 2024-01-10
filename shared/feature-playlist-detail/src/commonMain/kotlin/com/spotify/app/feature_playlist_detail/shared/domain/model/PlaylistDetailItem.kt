package com.spotify.app.feature_playlist_detail.shared.domain.model

data class PlaylistDetailItem(
    val id: String,
    val playlistId: String,
    val trackName: String,
    val artists: String,
    val image: String,
    val limit: Long,
    val offset: Long,
    val totalItemCount: Long
)