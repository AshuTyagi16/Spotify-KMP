package com.spotify.app.feature_album_detail.shared.data.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.spotify.app.feature_album_detail.shared.AlbumDetailDatabase
import com.spotify.app.feature_album_detail.shared.domain.model.AlbumDetailItem
import com.spotify.app.feature_album_detail.shared.domain.model.FetchAlbumDetailRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

internal class AlbumDetailLocalDataSource(database: AlbumDetailDatabase) {

    private val dbQuery = database.albumDetailDatabaseQueries

    suspend fun insertAlbumDetail(
        fetchAlbumDetailRequest: FetchAlbumDetailRequest,
        albumDetailItems: List<AlbumDetailItem>
    ) =
        withContext(Dispatchers.IO) {
            dbQuery.transaction {
                albumDetailItems.forEach {
                    dbQuery.insertAlbumDetailItem(
                        id = it.id,
                        artists = it.artists,
                        albumId = fetchAlbumDetailRequest.albumId,
                        trackName = it.trackName,
                        limitValue = fetchAlbumDetailRequest.limit,
                        offsetValue = fetchAlbumDetailRequest.offset,
                        total = it.total
                    )
                }
            }
        }

    suspend fun deleteAlbumDetail(albumId: String) = withContext(Dispatchers.IO) {
        dbQuery.transaction {
            dbQuery.deleteAllAlbumDetailItem(albumId)
        }
    }

    suspend fun fetchTotalItemCountForAlbum(albumId: String) = withContext(Dispatchers.IO) {
        dbQuery.fetchTotalItemCountForAlbum(albumId).executeAsOne()
    }

    fun fetchAlbumDetailItems(
        albumId: String,
        limit: Long,
        offset: Long
    ) = dbQuery.fetchAlbumDetailItem(
        albumId = albumId,
        limitValue = limit,
        offsetValue = offset
    )
        .asFlow()
        .mapToList(Dispatchers.IO)
}