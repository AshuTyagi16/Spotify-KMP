package com.spotify.app.feature_playlist_detail.shared.domain.repository

import com.spotify.app.core_base.shared.util.BaseConstants
import com.spotify.app.core_base.shared.util.CacheExpirationUtil
import com.spotify.app.core_network.shared.impl.data.model.RestClientResult
import com.spotify.app.core_network.shared.impl.exception.RestClientException
import com.spotify.app.core_network.shared.impl.util.mapFromDTO
import com.spotify.app.feature_playlist_detail.shared.data.local.PlaylistDetailLocalDataSource
import com.spotify.app.feature_playlist_detail.shared.data.network.PlaylistDetailRemoteDataSource
import com.spotify.app.feature_playlist_detail.shared.data.repository.PlaylistDetailRepository
import com.spotify.app.feature_playlist_detail.shared.domain.mapper.PlaylistDetailDtoMapper
import com.spotify.app.feature_playlist_detail.shared.domain.mapper.PlaylistDetailItemEntityMapper
import com.spotify.app.feature_playlist_detail.shared.domain.model.FetchPlaylistDetailRequest
import com.spotify.app.feature_playlist_detail.shared.domain.model.PlaylistDetail
import kotlinx.coroutines.flow.map
import org.mobilenativefoundation.store.store5.Fetcher
import org.mobilenativefoundation.store.store5.MemoryPolicy
import org.mobilenativefoundation.store.store5.SourceOfTruth
import org.mobilenativefoundation.store.store5.StoreBuilder
import org.mobilenativefoundation.store.store5.impl.extensions.fresh
import org.mobilenativefoundation.store.store5.impl.extensions.get

internal class PlaylistDetailRepositoryImpl(
    private val playlistDetailRemoteDataSource: PlaylistDetailRemoteDataSource,
    private val playlistDetailLocalDataSource: PlaylistDetailLocalDataSource,
    private val cacheExpirationUtil: CacheExpirationUtil
) : PlaylistDetailRepository {

    private val store =
        StoreBuilder.from<FetchPlaylistDetailRequest, RestClientResult<PlaylistDetail>, RestClientResult<PlaylistDetail>>(
            fetcher = Fetcher.of {
                val playlistDetailDtoMapper = PlaylistDetailDtoMapper(it)
                val result = playlistDetailRemoteDataSource.fetchPlaylistDetail(
                    playlistId = it.playlistId,
                    limit = it.limit,
                    offset = it.offset
                )
                    .mapFromDTO {
                        playlistDetailDtoMapper.asDomain(it!!)
                    }
                if (result.status == RestClientResult.Status.SUCCESS) {
                    result
                } else {
                    throw RestClientException(errorMessage = result.errorMessage.orEmpty())
                }
            },
            sourceOfTruth = SourceOfTruth.of(
                reader = { key ->
                    playlistDetailLocalDataSource.fetchPlaylistDetailItems(
                        playlistId = key.playlistId,
                        limit = key.limit,
                        offset = key.offset
                    ).map {
                        RestClientResult.success(
                            PlaylistDetail(
                                items = it.map { PlaylistDetailItemEntityMapper.asDomain(it) },
                                limit = it.first().limitValue,
                                offset = it.first().offsetValue,
                                total = it.first().total
                            )
                        )
                    }
                },
                writer = { key, input ->
                    if (input.status == RestClientResult.Status.SUCCESS) {
                        cacheExpirationUtil.setPlaylistDetailLastWrittenTimestamp(
                            playlistId = key.playlistId,
                            limit = key.limit,
                            offset = key.offset
                        )
                        playlistDetailLocalDataSource.insertPlaylistDetail(
                            playlistDetailItems = input.data?.items.orEmpty(),
                            totalItemCount = input.data?.total ?: 0L,
                            fetchPlaylistDetailRequest = key
                        )
                    }
                }
            )
        )
            .cachePolicy(
                MemoryPolicy.builder<FetchPlaylistDetailRequest, RestClientResult<PlaylistDetail>>()
                    .setExpireAfterWrite(BaseConstants.CACHE_EXPIRE_TIME)
                    .build()
            )
            .build()

    override suspend fun fetchPlaylistDetail(
        fetchPlaylistDetailRequest: FetchPlaylistDetailRequest
    ): RestClientResult<PlaylistDetail> {
        val isCacheExpired = cacheExpirationUtil.isPlaylistDetailCacheExpired(
            playlistId = fetchPlaylistDetailRequest.playlistId,
            limit = fetchPlaylistDetailRequest.limit,
            offset = fetchPlaylistDetailRequest.offset
        )
        return try {
            if (isCacheExpired) {
                store.fresh(fetchPlaylistDetailRequest)
            } else {
                store.get(fetchPlaylistDetailRequest)
            }
        } catch (e: RestClientException) {
            RestClientResult.error(errorMessage = e.errorMessage)
        }
    }
}