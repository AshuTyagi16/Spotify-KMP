package com.spotify.app.feature_album_detail.shared.domain.use_case.impl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.spotify.app.core_base.shared.util.BaseConstants
import com.spotify.app.feature_album_detail.shared.domain.model.AlbumDetailItem
import com.spotify.app.feature_album_detail.shared.domain.model.FetchAlbumDetailRequest
import com.spotify.app.feature_album_detail.shared.domain.paging_source.AlbumDetailPagingSource
import com.spotify.app.feature_album_detail.shared.domain.use_case.FetchAlbumDetailUseCase
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf

internal class FetchAlbumDetailUseCaseImpl : FetchAlbumDetailUseCase, KoinComponent {

    override suspend fun fetchAlbumDetail(fetchAlbumDetailRequest: FetchAlbumDetailRequest): Flow<PagingData<AlbumDetailItem>> {
        val albumDetailPagingSource by inject<AlbumDetailPagingSource> {
            parametersOf(
                fetchAlbumDetailRequest.albumId
            )
        }

        return Pager(
            config = PagingConfig(
                pageSize = BaseConstants.DEFAULT_PAGE_SIZE.toInt(),
                enablePlaceholders = false,
                initialLoadSize = BaseConstants.DEFAULT_PAGE_SIZE.toInt()
            )
        ) {
            albumDetailPagingSource
        }
            .flow
    }
}