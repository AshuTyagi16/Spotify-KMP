package com.spotify.app.feature_playlist_detail.shared.data.repository

import com.spotify.app.core_network.shared.impl.data.model.RestClientResult
import com.spotify.app.feature_playlist_detail.shared.domain.model.FetchPlaylistDetailRequest
import com.spotify.app.feature_playlist_detail.shared.domain.model.PlaylistDetail
import com.spotify.app.feature_playlist_detail.shared.domain.model.PlaylistDetailItem

internal interface PlaylistDetailRepository {
    suspend fun fetchPlaylistDetail(
        fetchPlaylistDetailRequest: FetchPlaylistDetailRequest
    ): RestClientResult<PlaylistDetail>

}