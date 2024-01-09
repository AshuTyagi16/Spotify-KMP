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
        val lastWrittenTimestampInMillis = preferenceUtilApi.fetchPlaylistDataLastWrittenTimestamp()
        isCacheExpired(lastWrittenTimestampInMillis)
    }

    suspend fun setPlaylistWrittenTimestamp() = withContext(Dispatchers.IO) {
        preferenceUtilApi.setPlaylistDataLastWrittenTimestamp(Clock.System.now().toEpochMilliseconds())
    }

    suspend fun isAlbumCacheExpired(): Boolean = withContext(Dispatchers.IO) {
        val lastWrittenTimestampInMillis = preferenceUtilApi.fetchAlbumDataLastWrittenTimestamp()
        isCacheExpired(lastWrittenTimestampInMillis)
    }

    suspend fun setAlbumWrittenTimestamp() = withContext(Dispatchers.IO) {
        preferenceUtilApi.setAlbumDataLastWrittenTimestamp(Clock.System.now().toEpochMilliseconds())
    }

    private fun isCacheExpired(lastWrittenTimestampInMillis: Long?): Boolean {
        return if (lastWrittenTimestampInMillis != null) {
            val currentTimestamp = Clock.System.now()
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
}