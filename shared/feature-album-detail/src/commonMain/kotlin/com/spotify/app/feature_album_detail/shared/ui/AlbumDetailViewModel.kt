package com.spotify.app.feature_album_detail.shared.ui

import androidx.paging.PagingData
import com.spotify.app.core_base.shared.models.ViewModel
import com.spotify.app.feature_album_detail.shared.domain.model.AlbumDetailItem
import com.spotify.app.feature_album_detail.shared.domain.model.FetchAlbumDetailRequest
import com.spotify.app.feature_album_detail.shared.domain.use_case.FetchAlbumDetailUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest

class AlbumDetailViewModel(
    private val fetchAlbumDetailUseCase: FetchAlbumDetailUseCase
) : ViewModel() {

    private val _pagingData = MutableSharedFlow<PagingData<AlbumDetailItem>>()
    val pagingData: Flow<PagingData<AlbumDetailItem>>
        get() = _pagingData

    suspend fun fetchAlbumDetail(fetchAlbumDetailRequest: FetchAlbumDetailRequest) {
        fetchAlbumDetailUseCase.fetchAlbumDetail(fetchAlbumDetailRequest).collectLatest {
            _pagingData.emit(it)
        }
    }
}