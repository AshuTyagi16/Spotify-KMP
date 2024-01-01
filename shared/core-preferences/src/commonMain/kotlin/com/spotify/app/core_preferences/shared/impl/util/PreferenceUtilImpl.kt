package com.spotify.app.core_preferences.shared.impl.util

import com.spotify.app.core_preferences.shared.api.PreferenceApi
import com.spotify.app.core_preferences.shared.api.PreferenceUtilApi

class PreferenceUtilImpl constructor(
    private val preferenceApi: PreferenceApi
) : PreferenceUtilApi {

    override suspend fun getAccessToken() =
        preferenceApi.getString(PreferenceConstants.ACCESS_TOKEN)

    override suspend fun setAccessToken(token: String) =
        preferenceApi.putString(PreferenceConstants.ACCESS_TOKEN, token)

    override suspend fun getRefreshToken() =
        preferenceApi.getString(PreferenceConstants.REFRESH_TOKEN)

    override suspend fun setRefreshToken(token: String) =
        preferenceApi.putString(PreferenceConstants.REFRESH_TOKEN, token)
}