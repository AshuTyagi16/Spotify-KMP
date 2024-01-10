package com.spotify.app.feature_playlist_detail.shared.domain.model

import com.spotify.app.core_base.shared.domain.model.AlbumItem

data class Track(
    val album: AlbumItem,
    val durationMs: Int,
    val episode: Boolean,
    val explicit: Boolean,
    val id: String,
    val name: String,
    val popularity: Int,
    val previewUrl: String?,
    val track: Boolean,
    val trackNumber: Int
)