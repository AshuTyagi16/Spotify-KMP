package com.spotify.app.feature_playlist_detail.shared.domain.use_case

import androidx.paging.PagingData
import com.spotify.app.feature_playlist_detail.shared.domain.model.FetchPlaylistDetailRequest
import com.spotify.app.feature_playlist_detail.shared.domain.model.PlaylistDetailItem
import kotlinx.coroutines.flow.Flow

interface FetchPlaylistDetailUseCase {
    suspend fun fetchPlaylistDetail(fetchPlaylistDetailRequest: FetchPlaylistDetailRequest): Flow<PagingData<PlaylistDetailItem>>
}