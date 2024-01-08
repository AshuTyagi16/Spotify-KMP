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

    override suspend fun setPlaylistLastWrittenTimestamp(timestamp: Long) {
        preferenceApi.putLong(PreferenceConstants.PLAYLIST_LAST_FETCHED_TIMESTAMP, timestamp)
    }

}