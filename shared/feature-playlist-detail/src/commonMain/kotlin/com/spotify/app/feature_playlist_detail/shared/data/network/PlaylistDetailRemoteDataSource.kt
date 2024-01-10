package com.spotify.app.feature_playlist_detail.shared.data.network

import com.spotify.app.core_network.shared.impl.data.base.BaseDataSource
import com.spotify.app.feature_playlist_detail.shared.data.dto.PlaylistDetailDTO
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.url

internal class PlaylistDetailRemoteDataSource(
    private val client: HttpClient
) : BaseDataSource() {

    suspend fun fetchPlaylistDetail(
        playlistId: String,
        limit: Long,
        offset: Long
    ) = getResult<PlaylistDetailDTO> {
        client.get {
            url("v1/playlists/$playlistId/tracks?limit=$limit&offset=$offset")
        }
    }
}