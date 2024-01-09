package com.spotify.app.core_preferences.shared.api

interface PreferenceUtilApi {
    suspend fun getAccessToken(): String?

    suspend fun setAccessToken(token: String)

    suspend fun clearAccessToken()

    suspend fun fetchPlaylistDataLastWrittenTimestamp(): Long?

    suspend fun setPlaylistDataLastWrittenTimestamp(timestamp: Long)

    suspend fun fetchAlbumDataLastWrittenTimestamp(): Long?

    suspend fun setAlbumDataLastWrittenTimestamp(timestamp: Long)
}