package com.spotify.app.feature_homepage.shared.domain.model

import com.spotify.app.core_base.shared.domain.model.Image

data class PlaylistItem(
    val description: String,

    val id: String,

    val images: List<Image>,

    val name: String,

    val tracks: TracksInfo,

    val type: String,

    val uri: String
)