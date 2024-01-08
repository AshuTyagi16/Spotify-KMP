package com.spotify.app.feature_homepage.shared.data.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.spotify.app.feature_homepage.shared.HomePageDatabase
import com.spotify.app.feature_homepage.shared.domain.model.playlist.PlaylistItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

internal class HomePageLocalDataSource(database: HomePageDatabase) {

    private val dbQuery = database.homePageDatabaseQueries

    suspend fun insertFeaturedPlaylists(featuredPlaylists: List<PlaylistItem>) =
        withContext(Dispatchers.IO) {
            dbQuery.transaction {
                dbQuery.deleteAllPlaylists()
                featuredPlaylists.forEach {
                    dbQuery.insertFeaturePlaylist(
                        id = it.id,
                        description = it.description,
                        name = it.name,
                        image = it.image,
                        trackCount = it.trackCount
                    )
                }
            }
        }

    suspend fun deleteFeaturedPlaylists() = withContext(Dispatchers.IO) {
        dbQuery.transaction {
            dbQuery.deleteAllPlaylists()
        }
    }

    fun fetchFeaturedPlaylists() = dbQuery.fetchFeaturedPlaylists()
        .asFlow()
        .mapToList(Dispatchers.IO)

    suspend fun fetchFeaturePlaylistsCount() = withContext(Dispatchers.IO) {
        dbQuery.fetchFeaturedPlaylists().executeAsList().size
    }

}