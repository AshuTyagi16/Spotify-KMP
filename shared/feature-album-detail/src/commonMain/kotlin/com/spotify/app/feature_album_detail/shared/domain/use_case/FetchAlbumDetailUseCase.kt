package com.spotify.app.feature_album_detail.shared.domain.use_case

import androidx.paging.PagingData
import com.spotify.app.feature_album_detail.shared.domain.model.AlbumDetailItem
import com.spotify.app.feature_album_detail.shared.domain.model.FetchAlbumDetailRequest
import kotlinx.coroutines.flow.Flow

interface FetchAlbumDetailUseCase {
    suspend fun fetchAlbumDetail(fetchAlbumDetailRequest: FetchAlbumDetailRequest): Flow<PagingData<AlbumDetailItem>>
}