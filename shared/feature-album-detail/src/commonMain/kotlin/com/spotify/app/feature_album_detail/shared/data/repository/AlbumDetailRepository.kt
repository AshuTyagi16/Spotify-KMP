package com.spotify.app.feature_album_detail.shared.data.repository

import com.spotify.app.core_network.shared.impl.data.model.RestClientResult
import com.spotify.app.feature_album_detail.shared.domain.model.AlbumDetailItem
import com.spotify.app.feature_album_detail.shared.domain.model.FetchAlbumDetailRequest

internal interface AlbumDetailRepository {
    suspend fun fetchAlbumDetail(
        fetchAlbumDetailRequest: FetchAlbumDetailRequest
    ): RestClientResult<List<AlbumDetailItem>>

}