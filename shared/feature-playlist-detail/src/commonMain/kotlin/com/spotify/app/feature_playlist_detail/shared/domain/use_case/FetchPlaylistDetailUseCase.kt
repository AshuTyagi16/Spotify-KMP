package com.spotify.app.feature_playlist_detail.shared.domain.use_case

import com.spotify.app.core_network.shared.impl.data.model.RestClientResult
import com.spotify.app.feature_playlist_detail.shared.domain.model.FetchPlaylistDetailRequest
import com.spotify.app.feature_playlist_detail.shared.domain.model.PlaylistDetailItem
import kotlinx.coroutines.flow.Flow

@Suppress("FUN_INTERFACE_WITH_SUSPEND_FUNCTION") // TODO: Remove once KTIJ-7642 is fixed
fun interface FetchPlaylistDetailUseCase {
    suspend fun fetchPlaylistDetail(fetchPlaylistDetailRequest: FetchPlaylistDetailRequest): Flow<RestClientResult<List<PlaylistDetailItem>>>
}