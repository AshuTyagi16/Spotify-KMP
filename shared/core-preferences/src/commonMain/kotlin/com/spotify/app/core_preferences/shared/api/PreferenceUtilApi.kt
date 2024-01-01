package com.spotify.app.core_preferences.shared.api
interface PreferenceUtilApi {
    suspend fun getAccessToken(): String?

    suspend fun setAccessToken(token: String)

    suspend fun getRefreshToken(): String?

    suspend fun setRefreshToken(token: String)
}