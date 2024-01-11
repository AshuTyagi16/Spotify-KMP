package com.spotify.app.feature_album_detail.shared.domain.repository

import com.spotify.app.core_base.shared.util.BaseConstants
import com.spotify.app.core_base.shared.util.CacheExpirationUtil
import com.spotify.app.core_network.shared.impl.data.model.RestClientResult
import com.spotify.app.core_network.shared.impl.exception.RestClientException
import com.spotify.app.core_network.shared.impl.util.mapFromDTO
import com.spotify.app.feature_album_detail.shared.data.local.AlbumDetailLocalDataSource
import com.spotify.app.feature_album_detail.shared.data.network.AlbumDetailRemoteDataSource
import com.spotify.app.feature_album_detail.shared.data.repository.AlbumDetailRepository
import com.spotify.app.feature_album_detail.shared.domain.model.AlbumDetailItem
import com.spotify.app.feature_album_detail.shared.domain.model.FetchAlbumDetailRequest
import com.spotify.app.feature_album_detail.shared.util.toAlbumDetailItemList
import com.spotify.app.feature_album_detail.shared.util.toAlbumDetailList
import kotlinx.coroutines.flow.map
import org.mobilenativefoundation.store.store5.Fetcher
import org.mobilenativefoundation.store.store5.MemoryPolicy
import org.mobilenativefoundation.store.store5.SourceOfTruth
import org.mobilenativefoundation.store.store5.StoreBuilder
import org.mobilenativefoundation.store.store5.impl.extensions.fresh
import org.mobilenativefoundation.store.store5.impl.extensions.get

internal class AlbumDetailRepositoryImpl(
    private val albumDetailRemoteDataSource: AlbumDetailRemoteDataSource,
    private val albumDetailLocalDataSource: AlbumDetailLocalDataSource,
    private val cacheExpirationUtil: CacheExpirationUtil
) : AlbumDetailRepository {

    private val store =
        StoreBuilder.from<FetchAlbumDetailRequest, RestClientResult<List<AlbumDetailItem>>, RestClientResult<List<AlbumDetailItem>>>(
            fetcher = Fetcher.of { key ->
                val result = albumDetailRemoteDataSource.fetchAlbumDetail(
                    albumId = key.albumId,
                    limit = key.limit,
                    offset = key.offset
                )
                    .mapFromDTO { albumDetail ->
                        albumDetail?.toAlbumDetailItemList(key).orEmpty()
                    }
                if (result.status == RestClientResult.Status.SUCCESS) {
                    result
                } else {
                    throw RestClientException(errorMessage = result.errorMessage.orEmpty())
                }
            },
            sourceOfTruth = SourceOfTruth.of(
                reader = { key ->
                    albumDetailLocalDataSource.fetchAlbumDetailItems(
                        albumId = key.albumId,
                        limit = key.limit,
                        offset = key.offset
                    ).map {
                        RestClientResult.success(it.toAlbumDetailList())
                    }
                },
                writer = { key, input ->
                    if (input.status == RestClientResult.Status.SUCCESS) {
                        cacheExpirationUtil.setAlbumDetailLastWrittenTimestamp(
                            albumId = key.albumId,
                            limit = key.limit,
                            offset = key.offset
                        )
                        albumDetailLocalDataSource.insertAlbumDetail(
                            albumDetailItems = input.data.orEmpty(),
                            fetchAlbumDetailRequest = key
                        )
                    }
                }
            )
        )
            .cachePolicy(
                MemoryPolicy.builder<FetchAlbumDetailRequest, RestClientResult<List<AlbumDetailItem>>>()
                    .setExpireAfterWrite(BaseConstants.CACHE_EXPIRE_TIME)
                    .build()
            )
            .build()

    override suspend fun fetchAlbumDetail(fetchAlbumDetailRequest: FetchAlbumDetailRequest): RestClientResult<List<AlbumDetailItem>> {
        val isCacheExpired = cacheExpirationUtil.isAlbumDetailCacheExpired(
            albumId = fetchAlbumDetailRequest.albumId,
            limit = fetchAlbumDetailRequest.limit,
            offset = fetchAlbumDetailRequest.offset
        )
        return try {
            if (isCacheExpired) {
                store.fresh(fetchAlbumDetailRequest)
            } else {
                store.get(fetchAlbumDetailRequest)
            }
        } catch (e: RestClientException) {
            RestClientResult.error(errorMessage = e.errorMessage)
        }
    }
}