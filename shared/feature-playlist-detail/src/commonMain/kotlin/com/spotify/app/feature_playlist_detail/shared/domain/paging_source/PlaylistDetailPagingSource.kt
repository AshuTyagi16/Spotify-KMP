package com.spotify.app.feature_playlist_detail.shared.domain.paging_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.spotify.app.core_network.shared.impl.data.model.RestClientResult
import com.spotify.app.feature_playlist_detail.shared.data.repository.PlaylistDetailRepository
import com.spotify.app.feature_playlist_detail.shared.domain.model.FetchPlaylistDetailRequest
import com.spotify.app.feature_playlist_detail.shared.domain.model.PlaylistDetailItem

internal class PlaylistDetailPagingSource(
    private val playlistId: String,
    private val playlistDetailRepository: PlaylistDetailRepository
) :
    PagingSource<FetchPlaylistDetailRequest, PlaylistDetailItem>() {
    override fun getRefreshKey(state: PagingState<FetchPlaylistDetailRequest, PlaylistDetailItem>): FetchPlaylistDetailRequest? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
                ?: state.closestPageToPosition(anchorPosition)?.nextKey
        }
    }

    override suspend fun load(params: LoadParams<FetchPlaylistDetailRequest>): LoadResult<FetchPlaylistDetailRequest, PlaylistDetailItem> {
        val key = params.key ?: FetchPlaylistDetailRequest(
            playlistId = playlistId,
            limit = params.loadSize.toLong(),
            offset = 0
        )
        val result = playlistDetailRepository.fetchPlaylistDetail(key)
        return if (result.status == RestClientResult.Status.SUCCESS) {
            val list = result.data.orEmpty()
            LoadResult.Page(
                data = list,
                prevKey = if (key.offset <= 0) {
                    null
                } else {
                    FetchPlaylistDetailRequest(
                        playlistId = key.playlistId,
                        limit = key.limit,
                        offset = key.offset - key.limit
                    )
                },
                nextKey = if ((result.data?.firstOrNull()?.totalItemCount ?: 0) > key.offset + key.limit) {
                    FetchPlaylistDetailRequest(
                        playlistId = key.playlistId,
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