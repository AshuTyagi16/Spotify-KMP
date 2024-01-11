package com.spotify.app.feature_album_detail.shared.domain.paging_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.spotify.app.core_base.shared.util.BaseConstants
import com.spotify.app.core_network.shared.impl.data.model.RestClientResult
import com.spotify.app.feature_album_detail.shared.data.repository.AlbumDetailRepository
import com.spotify.app.feature_album_detail.shared.domain.model.AlbumDetailItem
import com.spotify.app.feature_album_detail.shared.domain.model.FetchAlbumDetailRequest

internal class AlbumDetailPagingSource(
    private val albumId: String,
    private val albumDetailRepository: AlbumDetailRepository
) :
    PagingSource<FetchAlbumDetailRequest, AlbumDetailItem>() {

    override fun getRefreshKey(state: PagingState<FetchAlbumDetailRequest, AlbumDetailItem>): FetchAlbumDetailRequest? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
                ?: state.closestPageToPosition(anchorPosition)?.nextKey
        }
    }

    override suspend fun load(params: LoadParams<FetchAlbumDetailRequest>): LoadResult<FetchAlbumDetailRequest, AlbumDetailItem> {
        val key = params.key ?: FetchAlbumDetailRequest(
            albumId = albumId,
            limit = params.loadSize.toLong(),
            offset = BaseConstants.DEFAULT_OFFSET
        )
        val result = albumDetailRepository.fetchAlbumDetail(key)
        return if (result.status == RestClientResult.Status.SUCCESS) {
            val list = result.data.orEmpty()
            LoadResult.Page(
                data = list,
                prevKey = if (key.offset <= 0) {
                    null
                } else {
                    FetchAlbumDetailRequest(
                        albumId = key.albumId,
                        limit = key.limit,
                        offset = key.offset - key.limit
                    )
                },
                nextKey = if ((result.data?.firstOrNull()?.total ?: 0) > key.offset + key.limit
                ) {
                    FetchAlbumDetailRequest(
                        albumId = key.albumId,
                        limit = key.limit,
                        offset = key.offset + key.limit
                    )
                } else {
                    null
                }
            )
        } else {
            LoadResult.Error(Throwable(message = result.errorMessage))
        }
    }
}