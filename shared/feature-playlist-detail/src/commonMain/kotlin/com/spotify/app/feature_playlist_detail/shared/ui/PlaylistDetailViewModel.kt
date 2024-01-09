package com.spotify.app.feature_playlist_detail.shared.ui

import com.spotify.app.core_base.shared.models.ViewModel
import com.spotify.app.core_network.shared.impl.data.model.RestClientResult
import com.spotify.app.feature_playlist_detail.shared.domain.model.FetchPlaylistDetailRequest
import com.spotify.app.feature_playlist_detail.shared.domain.model.PlaylistDetailItem
import com.spotify.app.feature_playlist_detail.shared.domain.use_case.FetchPlaylistDetailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest

class PlaylistDetailViewModel(
    private val fetchPlaylistDetailUseCase: FetchPlaylistDetailUseCase
) : ViewModel() {

    val data = MutableStateFlow<RestClientResult<List<PlaylistDetailItem>>>(RestClientResult.idle())

    suspend fun fetchPlaylistDetail(fetchPlaylistDetailRequest: FetchPlaylistDetailRequest) {
        fetchPlaylistDetailUseCase.fetchPlaylistDetail(fetchPlaylistDetailRequest).collectLatest {
            data.emit(it)
        }
    }
}