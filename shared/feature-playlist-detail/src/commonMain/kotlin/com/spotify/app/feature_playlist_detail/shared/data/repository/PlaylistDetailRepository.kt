package com.spotify.app.feature_playlist_detail.shared.data.repository

import com.spotify.app.core_network.shared.impl.data.model.RestClientResult
import com.spotify.app.feature_playlist_detail.shared.domain.model.FetchPlaylistDetailRequest
import com.spotify.app.feature_playlist_detail.shared.domain.model.PlaylistDetailItem
import kotlinx.coroutines.flow.Flow

internal interface PlaylistDetailRepository {
    suspend fun fetchPlaylistDetail(
        fetchPlaylistDetailRequest: FetchPlaylistDetailRequest
    ): Flow<RestClientResult<List<PlaylistDetailItem>>>

}