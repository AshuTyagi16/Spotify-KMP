package com.spotify.app.feature_homepage.shared.domain.repository

import com.spotify.app.core_base.shared.domain.model.AlbumItem
import com.spotify.app.core_base.shared.util.BaseConstants
import com.spotify.app.core_base.shared.util.CacheExpirationUtil
import com.spotify.app.core_network.shared.impl.data.model.RestClientResult
import com.spotify.app.core_network.shared.impl.exception.RestClientException
import com.spotify.app.core_network.shared.impl.util.mapFromDTO
import com.spotify.app.feature_homepage.shared.data.local.HomePageLocalDataSource
import com.spotify.app.feature_homepage.shared.data.network.HomePageRemoteDataSource
import com.spotify.app.feature_homepage.shared.data.repository.HomePageRepository
import com.spotify.app.feature_homepage.shared.domain.model.playlist.PlaylistItem
import com.spotify.app.feature_homepage.shared.util.toAlbumItemList
import com.spotify.app.feature_homepage.shared.util.toPlaylistItemList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import org.mobilenativefoundation.store.store5.Fetcher
import org.mobilenativefoundation.store.store5.MemoryPolicy
import org.mobilenativefoundation.store.store5.SourceOfTruth
import org.mobilenativefoundation.store.store5.StoreBuilder
import org.mobilenativefoundation.store.store5.StoreReadRequest
import org.mobilenativefoundation.store.store5.StoreReadResponse

internal class HomePageRepositoryImpl(
    private val homePageRemoteDataSource: HomePageRemoteDataSource,
    private val homePageLocalDataSource: HomePageLocalDataSource,
    private val cacheExpirationUtil: CacheExpirationUtil
) : HomePageRepository {

    companion object {
        private const val FEATURED_PLAYLISTS_CACHE_KEY = "feature-playlists"
        private const val FEATURED_ALBUMS_CACHE_KEY = "feature-albums"
    }

    private val featurePlaylistStore =
        StoreBuilder.from<String, RestClientResult<List<PlaylistItem>>, RestClientResult<List<PlaylistItem>>>(
            fetcher = Fetcher.of {
                val result = homePageRemoteDataSource.fetchFeaturedPlaylist()
                    .mapFromDTO {
                        it?.toPlaylistItemList().orEmpty()
                    }
                if (result.status == RestClientResult.Status.SUCCESS) {
                    result
                } else {
                    throw RestClientException(
                        errorMessage = result.errorMessage.orEmpty()
                    )
                }
            },
            sourceOfTruth = SourceOfTruth.of(
                reader = {
                    homePageLocalDataSource.fetchFeaturedPlaylists()
                        .map {
                            RestClientResult.success(it.toPlaylistItemList())
                        }
                },
                writer = { _, input ->
                    if (input.status == RestClientResult.Status.SUCCESS) {
                        cacheExpirationUtil.setPlaylistWrittenTimestamp()
                        homePageLocalDataSource.insertFeaturedPlaylists(input.data.orEmpty())
                    }
                }
            )
        )
            .cachePolicy(
                MemoryPolicy.builder<String, RestClientResult<List<PlaylistItem>>>()
                    .setExpireAfterWrite(BaseConstants.CACHE_EXPIRE_TIME)
                    .build()
            )
            .build()

    private val featuredAlbumStore =
        StoreBuilder.from<String, RestClientResult<List<AlbumItem>>, RestClientResult<List<AlbumItem>>>(
            fetcher = Fetcher.of {
                val result = homePageRemoteDataSource.fetchFeaturedAlbums()
                    .mapFromDTO {
                        it?.toAlbumItemList().orEmpty()
                    }
                if (result.status == RestClientResult.Status.SUCCESS) {
                    result
                } else {
                    throw RestClientException(
                        errorMessage = result.errorMessage.orEmpty()
                    )
                }
            },
            sourceOfTruth = SourceOfTruth.of(
                reader = {
                    homePageLocalDataSource.fetchFeaturedAlbums()
                        .map {
                            RestClientResult.success(it.toAlbumItemList())
                        }
                },
                writer = { _, input ->
                    if (input.status == RestClientResult.Status.SUCCESS) {
                        cacheExpirationUtil.setAlbumWrittenTimestamp()
                        homePageLocalDataSource.insertFeaturedAlbums(input.data.orEmpty())
                    }
                }
            )
        )
            .cachePolicy(
                MemoryPolicy.builder<String, RestClientResult<List<AlbumItem>>>()
                    .setExpireAfterWrite(BaseConstants.CACHE_EXPIRE_TIME)
                    .build()
            )
            .build()

    override suspend fun fetchFeaturedPlaylists(): Flow<RestClientResult<List<PlaylistItem>>> {
        val isCacheExpired = cacheExpirationUtil.isPlaylistCacheExpired()
        return featurePlaylistStore.stream(
            StoreReadRequest.cached(
                key = FEATURED_PLAYLISTS_CACHE_KEY,
                refresh = isCacheExpired
            )
        )
            .flowOn(Dispatchers.IO)
            .map {
                when (it) {
                    is StoreReadResponse.Data -> {
                        it.value
                    }

                    is StoreReadResponse.Error.Exception -> {
                        RestClientResult.error(errorMessage = it.errorMessageOrNull().orEmpty())
                    }

                    is StoreReadResponse.Error.Message -> {
                        RestClientResult.error(errorMessage = it.errorMessageOrNull().orEmpty())
                    }

                    is StoreReadResponse.Loading -> {
                        RestClientResult.loading()
                    }

                    is StoreReadResponse.NoNewData -> {
                        RestClientResult.error(errorMessage = it.errorMessageOrNull().orEmpty())
                    }
                }
            }
    }

    override suspend fun fetchFeaturedAlbums(): Flow<RestClientResult<List<AlbumItem>>> {
        val isCacheExpired = cacheExpirationUtil.isAlbumCacheExpired()
        return featuredAlbumStore.stream(
            StoreReadRequest.cached(
                key = FEATURED_ALBUMS_CACHE_KEY,
                refresh = isCacheExpired
            )
        )
            .flowOn(Dispatchers.IO)
            .map {
                when (it) {
                    is StoreReadResponse.Data -> {
                        it.value
                    }

                    is StoreReadResponse.Error.Exception -> {
                        RestClientResult.error(errorMessage = it.errorMessageOrNull().orEmpty())
                    }

                    is StoreReadResponse.Error.Message -> {
                        RestClientResult.error(errorMessage = it.errorMessageOrNull().orEmpty())
                    }

                    is StoreReadResponse.Loading -> {
                        RestClientResult.loading()
                    }

                    is StoreReadResponse.NoNewData -> {
                        RestClientResult.error(errorMessage = it.errorMessageOrNull().orEmpty())
                    }
                }
            }
    }

}