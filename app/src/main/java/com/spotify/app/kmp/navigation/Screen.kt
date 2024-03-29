package com.spotify.app.kmp.navigation

sealed class Screen(val route: String) {

    data object HomePage : Screen("home_page")

    data object PlaylistDetail : Screen("playlist_detail")

    data object AlbumDetail : Screen("album_detail")

    fun withArgs(vararg args: Any): String {
        return buildString {
            append(route)
            args.forEach {
                append("/$it")
            }
        }
    }

}