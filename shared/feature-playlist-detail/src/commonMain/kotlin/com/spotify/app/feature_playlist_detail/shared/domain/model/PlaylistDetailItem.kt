package com.spotify.app.feature_playlist_detail.shared.domain.model

data class PlaylistDetailItem(
    val track: Track,
    val fetchPlaylistDetailRequest: FetchPlaylistDetailRequest,
    val totalItemCount: Long
)