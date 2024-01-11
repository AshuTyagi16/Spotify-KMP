package com.spotify.app.core_preferences.shared.api

interface PreferenceUtilApi {
    suspend fun getAccessToken(): String?

    suspend fun setAccessToken(token: String)

    suspend fun clearAccessToken()

    suspend fun fetchPlaylistDataLastWrittenTimestamp(): Long?

    suspend fun setPlaylistDataLastWrittenTimestamp(timestamp: Long)

    suspend fun fetchAlbumDataLastWrittenTimestamp(): Long?

    suspend fun setAlbumDataLastWrittenTimestamp(timestamp: Long)

    suspend fun fetchPlaylistDetailLastWrittenTimestamp(
        playlistId: String,
        limit: Long,
        offset: Long
    ): Long?

    suspend fun setPlaylistDetailLastWrittenTimestamp(
        playlistId: String,
        limit: Long,
        offset: Long,
        timestamp: Long
    )

    suspend fun fetchAlbumDetailLastWrittenTimestamp(
        albumId: String,
        limit: Long,
        offset: Long
    ): Long?

    suspend fun setAlbumDetailLastWrittenTimestamp(
        albumId: String,
        limit: Long,
        offset: Long,
        timestamp: Long
    )
}