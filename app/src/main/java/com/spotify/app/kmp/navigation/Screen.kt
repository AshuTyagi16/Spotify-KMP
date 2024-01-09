package com.spotify.app.kmp.navigation

sealed class Screen(val route: String) {

    data object HomePage : Screen("home_page")

    data object PlaylistTracks : Screen("playlist_tracks")

    data object AlbumTracks : Screen("album_tracks")

    fun withArgs(vararg args: Any): String {
        return buildString {
            append(route)
            args.forEach {
                append("/$it")
            }
        }
    }

}