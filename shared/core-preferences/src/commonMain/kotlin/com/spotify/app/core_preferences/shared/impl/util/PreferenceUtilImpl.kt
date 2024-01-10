package com.spotify.app.core_preferences.shared.impl.util

import com.spotify.app.core_preferences.shared.api.PreferenceApi
import com.spotify.app.core_preferences.shared.api.PreferenceUtilApi

internal class PreferenceUtilImpl constructor(
    private val preferenceApi: PreferenceApi
) : PreferenceUtilApi {

    override suspend fun getAccessToken() =
        preferenceApi.getString(PreferenceConstants.ACCESS_TOKEN)

    override suspend fun setAccessToken(token: String) =
        preferenceApi.putString(PreferenceConstants.ACCESS_TOKEN, token)

    override suspend fun clearAccessToken() {
        preferenceApi.remove(PreferenceConstants.ACCESS_TOKEN)
    }

    override suspend fun fetchPlaylistDataLastWrittenTimestamp(): Long? {
        return preferenceApi.getLong(PreferenceConstants.PLAYLIST_LAST_FETCHED_TIMESTAMP)
    }

    override suspend fun setPlaylistDataLastWrittenTimestamp(timestamp: Long) {
        preferenceApi.putLong(PreferenceConstants.PLAYLIST_LAST_FETCHED_TIMESTAMP, timestamp)
    }

    override suspend fun fetchAlbumDataLastWrittenTimestamp(): Long? {
        return preferenceApi.getLong(PreferenceConstants.ALBUM_LAST_FETCHED_TIMESTAMP)
    }

    override suspend fun setAlbumDataLastWrittenTimestamp(timestamp: Long) {
        preferenceApi.putLong(PreferenceConstants.ALBUM_LAST_FETCHED_TIMESTAMP, timestamp)
    }

    override suspend fun fetchPlaylistDetailLastWrittenTimestamp(
        playlistId: String,
        limit: Long,
        offset: Long
    ): Long? {
        val key = getKeyForPlaylistDetail(playlistId, limit, offset)
        return preferenceApi.getLong(key)
    }

    override suspend fun setPlaylistDetailLastWrittenTimestamp(
        playlistId: String,
        limit: Long,
        offset: Long,
        timestamp: Long
    ) {
        val key = getKeyForPlaylistDetail(playlistId, limit, offset)
        preferenceApi.putLong(key, timestamp)
    }

    private fun getKeyForPlaylistDetail(
        playlistId: String,
        limit: Long,
        offset: Long
    ): String {
        return "$playlistId-$limit-$offset"
    }

}