package com.spotify.app.core_preferences.shared.impl.util

import com.spotify.app.core_preferences.shared.api.PreferenceApi

class PreferenceUtil constructor(
    private val preferenceApi: PreferenceApi
) {

    suspend fun getAccessToken() = preferenceApi.getString(PreferenceConstants.ACCESS_TOKEN)

    suspend fun setAccessToken(token: String) =
        preferenceApi.putString(PreferenceConstants.ACCESS_TOKEN, token)

    suspend fun getRefreshToken() = preferenceApi.getString(PreferenceConstants.REFRESH_TOKEN)

    suspend fun setRefreshToken(token: String) =
        preferenceApi.putString(PreferenceConstants.REFRESH_TOKEN, token)
}