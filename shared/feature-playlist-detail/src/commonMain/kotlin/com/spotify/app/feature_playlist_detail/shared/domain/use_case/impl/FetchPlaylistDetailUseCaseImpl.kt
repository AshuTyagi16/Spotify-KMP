package com.spotify.app.feature_playlist_detail.shared.domain.use_case.impl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.spotify.app.core_base.shared.util.BaseConstants
import com.spotify.app.feature_playlist_detail.shared.domain.model.FetchPlaylistDetailRequest
import com.spotify.app.feature_playlist_detail.shared.domain.model.PlaylistDetailItem
import com.spotify.app.feature_playlist_detail.shared.domain.paging_source.PlaylistDetailPagingSource
import com.spotify.app.feature_playlist_detail.shared.domain.use_case.FetchPlaylistDetailUseCase
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf

internal class FetchPlaylistDetailUseCaseImpl : FetchPlaylistDetailUseCase, KoinComponent {

    override suspend fun fetchPlaylistDetail(fetchPlaylistDetailRequest: FetchPlaylistDetailRequest): Flow<PagingData<PlaylistDetailItem>> {
        val playlistDetailPagingSource by inject<PlaylistDetailPagingSource> {
            parametersOf(
                fetchPlaylistDetailRequest.playlistId
            )
        }

        return Pager(
            config = PagingConfig(
                pageSize = BaseConstants.DEFAULT_PAGE_SIZE.toInt(),
                enablePlaceholders = false,
                initialLoadSize = BaseConstants.DEFAULT_PAGE_SIZE.toInt()
            )
        ) {
            playlistDetailPagingSource
        }
            .flow
    }
}