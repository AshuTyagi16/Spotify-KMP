package com.spotify.app.feature_playlist_detail.shared.ui

import androidx.paging.PagingData
import com.spotify.app.core_base.shared.models.ViewModel
import com.spotify.app.core_base.shared.util.BaseConstants
import com.spotify.app.feature_playlist_detail.shared.domain.model.FetchPlaylistDetailRequest
import com.spotify.app.feature_playlist_detail.shared.domain.model.PlaylistDetailItem
import com.spotify.app.feature_playlist_detail.shared.domain.use_case.FetchPlaylistDetailUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest

class PlaylistDetailViewModel(
    private val fetchPlaylistDetailUseCase: FetchPlaylistDetailUseCase
) : ViewModel() {

    private val _pagingData = MutableSharedFlow<PagingData<PlaylistDetailItem>>()
    val pagingData: Flow<PagingData<PlaylistDetailItem>>
        get() = _pagingData

    suspend fun fetchPlaylistDetail(playlistId: String) {
        val request = FetchPlaylistDetailRequest(
            playlistId = playlistId,
            limit = BaseConstants.DEFAULT_PAGE_SIZE,
            offset = BaseConstants.DEFAULT_OFFSET
        )
        fetchPlaylistDetailUseCase.fetchPlaylistDetail(request).collectLatest {
            _pagingData.emit(it)
        }
    }
}