package com.spotify.app.feature_homepage.shared.domain.model.playlist

data class PlaylistItem(

    val id: String,

    val description: String,

    val name: String,

    val image: String,

    val trackCount: Long
)