package com.spotify.app.feature_playlist_detail.shared.domain.model

data class FetchPlaylistDetailRequest(
    val playlistId: String,
    val limit: Int,
    val offset: Int
)