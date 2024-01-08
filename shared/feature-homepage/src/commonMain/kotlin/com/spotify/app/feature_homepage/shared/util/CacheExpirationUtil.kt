package com.spotify.app.feature_homepage.shared.util

import com.spotify.app.core_preferences.shared.api.PreferenceUtilApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.until

internal class CacheExpirationUtil(
    private val preferenceUtilApi: PreferenceUtilApi
) {
    suspend fun isPlaylistCacheExpired(): Boolean = withContext(Dispatchers.IO) {
        val currentTimestamp = Clock.System.now()
        val lastWrittenTimestampInMillis = preferenceUtilApi.fetchPlaylistDataLastWrittenTimestamp()

        if (lastWrittenTimestampInMillis != null) {
            val lastWrittenTimestamp = Instant.fromEpochMilliseconds(lastWrittenTimestampInMillis)

            val secondsSinceLastWritten = lastWrittenTimestamp.until(
                currentTimestamp,
                DateTimeUnit.SECOND,
                TimeZone.currentSystemDefault()
            )

            val maxCacheExpiryInSeconds = FeatureHomePageConstants.CACHE_EXPIRE_TIME.inWholeSeconds
            secondsSinceLastWritten > maxCacheExpiryInSeconds
        } else {
            true
        }
    }

    suspend fun setPlaylistWrittenTimestamp() = withContext(Dispatchers.IO) {
        preferenceUtilApi.setPlaylistLastWrittenTimestamp(Clock.System.now().toEpochMilliseconds())
    }
}