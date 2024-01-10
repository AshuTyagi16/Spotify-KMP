package com.spotify.app.feature_homepage.shared.domain.repository

import com.spotify.app.core_base.shared.domain.model.AlbumItem
import com.spotify.app.core_base.shared.util.BaseConstants
import com.spotify.app.core_base.shared.util.CacheExpirationUtil
import com.spotify.app.core_network.shared.impl.data.model.RestClientResult
import com.spotify.app.core_network.shared.impl.util.mapFromDTO
import com.spotify.app.core_network.shared.impl.util.mapStoreResponseToRestClientResult
import com.spotify.app.feature_homepage.shared.data.local.HomePageLocalDataSource
import com.spotify.app.feature_homepage.shared.data.network.HomePageRemoteDataSource
import com.spotify.app.feature_homepage.shared.data.repository.HomePageRepository
import com.spotify.app.feature_homepage.shared.domain.mapper.album.AlbumItemEntityMapper
import com.spotify.app.feature_homepage.shared.domain.mapper.album.FeaturedAlbumDtoMapper
import com.spotify.app.feature_homepage.shared.domain.mapper.playlist.FeaturePlaylistsDtoMapper
import com.spotify.app.feature_homepage.shared.domain.mapper.playlist.PlaylistItemEntityMapper
import com.spotify.app.feature_homepage.shared.domain.model.album.FeaturedAlbums
import com.spotify.app.feature_homepage.shared.domain.model.playlist.FeaturedPlaylists
import com.spotify.app.feature_homepage.shared.domain.model.playlist.PlaylistItem
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
        StoreBuilder.from<String, RestClientResult<FeaturedPlaylists>, List<PlaylistItem>>(
            fetcher = Fetcher.of {
                homePageRemoteDataSource.fetchFeaturedPlaylist()
                    .mapFromDTO { FeaturePlaylistsDtoMapper.asDomain(it!!) }
            },
            sourceOfTruth = SourceOfTruth.of(
                reader = {
                    homePageLocalDataSource.fetchFeaturedPlaylists()
                        .map { it.map { PlaylistItemEntityMapper.asDomain(it) } }
                },
                writer = { _, input ->
                    if (input.status == RestClientResult.Status.SUCCESS) {
                        cacheExpirationUtil.setPlaylistWrittenTimestamp()
                        homePageLocalDataSource.insertFeaturedPlaylists(input.data?.playlists?.items.orEmpty())
                    }
                }
            )
        )
            .cachePolicy(
                MemoryPolicy.builder<String, List<PlaylistItem>>()
                    .setExpireAfterWrite(BaseConstants.CACHE_EXPIRE_TIME)
                    .build()
            )
            .build()

    private val featuredAlbumStore =
        StoreBuilder.from<String, RestClientResult<FeaturedAlbums>, List<AlbumItem>>(
            fetcher = Fetcher.of {
                homePageRemoteDataSource.fetchFeaturedAlbums()
                    .mapFromDTO { FeaturedAlbumDtoMapper.asDomain(it!!) }
            },
            sourceOfTruth = SourceOfTruth.of(
                reader = {
                    homePageLocalDataSource.fetchFeaturedAlbums()
                        .map { it.map { AlbumItemEntityMapper.asDomain(it) } }
                },
                writer = { _, input ->
                    if (input.status == RestClientResult.Status.SUCCESS) {
                        cacheExpirationUtil.setAlbumWrittenTimestamp()
                        homePageLocalDataSource.insertFeaturedAlbums(input.data?.albums?.items.orEmpty())
                    }
                }
            )
        )
            .cachePolicy(
                MemoryPolicy.builder<String, List<AlbumItem>>()
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
            .mapStoreResponseToRestClientResult()
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
            .mapStoreResponseToRestClientResult()
    }

}