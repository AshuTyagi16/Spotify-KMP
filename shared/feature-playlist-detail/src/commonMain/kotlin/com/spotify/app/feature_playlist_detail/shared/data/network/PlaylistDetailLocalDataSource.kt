package com.spotify.app.feature_playlist_detail.shared.data.network

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.spotify.app.feature_playlist_detail.shared.PlaylistDetailDatabase
import com.spotify.app.feature_playlist_detail.shared.domain.model.FetchPlaylistDetailRequest
import com.spotify.app.feature_playlist_detail.shared.domain.model.PlaylistDetailItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

internal class PlaylistDetailLocalDataSource(database: PlaylistDetailDatabase) {

    private val dbQuery = database.playlistDetailDatabaseQueries

    suspend fun insertPlaylistDetail(
        fetchPlaylistDetailRequest: FetchPlaylistDetailRequest,
        totalItemCount: Long,
        playlistDetailItems: List<PlaylistDetailItem>
    ) =
        withContext(Dispatchers.IO) {
            dbQuery.transaction {
                playlistDetailItems.forEach {
                    dbQuery.insertPlaylstDetailItem(
                        id = it.track.id,
                        artists = it.track.album.artists,
                        playlistId = fetchPlaylistDetailRequest.playlistId,
                        trackName = it.track.name,
                        image = it.track.album.image,
                        limitValue = fetchPlaylistDetailRequest.limit,
                        offsetValue = fetchPlaylistDetailRequest.offset,
                        total = totalItemCount
                    )
                }
            }
        }

    suspend fun deletePlaylistDetail(playlistId: String) = withContext(Dispatchers.IO) {
        dbQuery.transaction {
            dbQuery.deleteAllPlaylistDetailItem(playlistId)
        }
    }

    suspend fun fetchTotalItemCountForPlaylist(playlistId: String) = withContext(Dispatchers.IO) {
        dbQuery.fetchTotalItemCountForPlaylist(playlistId).executeAsOne()
    }

    fun fetchPlaylistDetailItems(
        playlistId: String,
        limit: Long,
        offset: Long
    ) = dbQuery.fetchPlaylistDetailItem(
        playlistId = playlistId,
        limitValue = limit,
        offsetValue = offset
    )
        .asFlow()
        .mapToList(Dispatchers.IO)
}