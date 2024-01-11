package com.spotify.app.feature_album_detail.shared.data.network

import com.spotify.app.core_network.shared.impl.data.base.BaseDataSource
import com.spotify.app.feature_album_detail.shared.data.dto.AlbumDetailResponseDTO
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.url

internal class AlbumDetailRemoteDataSource(
    private val client: HttpClient
) : BaseDataSource() {

    suspend fun fetchAlbumDetail(
        albumId: String,
        limit: Long,
        offset: Long
    ) = getResult<AlbumDetailResponseDTO> {
        client.get {
            url("v1/albums/$albumId/tracks?limit=$limit&offset=$offset")
        }
    }
}