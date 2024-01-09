package com.spotify.app.feature_playlist_detail.shared.domain.repository

import com.spotify.app.core_base.shared.util.BaseConstants
import com.spotify.app.core_network.shared.impl.data.model.RestClientResult
import com.spotify.app.core_network.shared.impl.util.mapFromDTO
import com.spotify.app.core_network.shared.impl.util.mapStoreResponseToRestClientResult
import com.spotify.app.feature_playlist_detail.shared.data.network.PlaylistDetailDataSource
import com.spotify.app.feature_playlist_detail.shared.data.repository.PlaylistDetailRepository
import com.spotify.app.feature_playlist_detail.shared.domain.mapper.PlaylistDetailDtoMapper
import com.spotify.app.feature_playlist_detail.shared.domain.model.FetchPlaylistDetailRequest
import com.spotify.app.feature_playlist_detail.shared.domain.model.PlaylistDetailItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import org.mobilenativefoundation.store.store5.Fetcher
import org.mobilenativefoundation.store.store5.MemoryPolicy
import org.mobilenativefoundation.store.store5.StoreBuilder
import org.mobilenativefoundation.store.store5.StoreReadRequest

internal class PlaylistDetailRepositoryImpl(
    private val playlistDetailDataSource: PlaylistDetailDataSource
) : PlaylistDetailRepository {

    private val store =
        StoreBuilder.from<FetchPlaylistDetailRequest, List<PlaylistDetailItem>>(
            fetcher = Fetcher.of {
                playlistDetailDataSource.fetchPlaylistDetail(
                    playlistId = it.playlistId,
                    limit = it.limit,
                    offset = it.offset
                )
                    .mapFromDTO {
                        PlaylistDetailDtoMapper.asDomain(it!!)
                    }
                    .data?.items.orEmpty()
            }
        )
            .cachePolicy(
                MemoryPolicy.builder<FetchPlaylistDetailRequest, List<PlaylistDetailItem>>()
                    .setExpireAfterWrite(BaseConstants.CACHE_EXPIRE_TIME)
                    .build()
            )
            .build()

    override suspend fun fetchPlaylistDetail(
        fetchPlaylistDetailRequest: FetchPlaylistDetailRequest
    ): Flow<RestClientResult<List<PlaylistDetailItem>>> {
        return store.stream(
            StoreReadRequest.cached(
                key = fetchPlaylistDetailRequest,
                refresh = false
            )
        )
            .flowOn(Dispatchers.IO)
            .mapStoreResponseToRestClientResult()
    }
}