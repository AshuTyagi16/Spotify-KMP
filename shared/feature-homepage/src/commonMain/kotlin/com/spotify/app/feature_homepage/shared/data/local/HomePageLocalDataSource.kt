package com.spotify.app.feature_homepage.shared.data.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.spotify.app.feature_homepage.shared.HomePageDatabase
import com.spotify.app.core_base.shared.domain.model.AlbumItem
import com.spotify.app.feature_homepage.shared.domain.model.playlist.PlaylistItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

internal class HomePageLocalDataSource(database: HomePageDatabase) {

    private val dbQuery = database.homePageDatabaseQueries

    suspend fun insertFeaturedPlaylists(featuredPlaylists: List<PlaylistItem>) =
        withContext(Dispatchers.IO) {
            dbQuery.transaction {
                dbQuery.deleteAllFeaturedPlaylists()
                featuredPlaylists.forEach {
                    dbQuery.insertFeaturedPlaylist(
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
            dbQuery.deleteAllFeaturedPlaylists()
        }
    }

    fun fetchFeaturedPlaylists() = dbQuery.fetchFeaturedPlaylists()
        .asFlow()
        .mapToList(Dispatchers.IO)


    suspend fun insertFeaturedAlbums(featuredAlbums: List<AlbumItem>) =
        withContext(Dispatchers.IO) {
            dbQuery.transaction {
                dbQuery.deleteAllFeaturedAlbums()
                featuredAlbums.forEach {
                    dbQuery.insertFeaturedAlbum(
                        id = it.id,
                        name = it.name,
                        image = it.image,
                        trackCount = it.trackCount,
                        releaseDate = it.releaseDate,
                        artists = it.artists
                    )
                }
            }
        }

    fun fetchFeaturedAlbums() = dbQuery.fetchFeaturedAlbums()
        .asFlow()
        .mapToList(Dispatchers.IO)

}