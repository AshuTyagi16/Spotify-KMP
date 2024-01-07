package com.spotify.app.feature_homepage.shared.domain.use_case.impl

import com.spotify.app.core_network.shared.impl.data.model.RestClientResult
import com.spotify.app.core_network.shared.impl.util.mapFromDTO
import com.spotify.app.core_network.shared.impl.util.mapStoreResponseToRestClientResult
import com.spotify.app.feature_homepage.shared.data.repository.HomePageRepository
import com.spotify.app.feature_homepage.shared.domain.mapper.FeaturePlaylistsDtoMapper
import com.spotify.app.feature_homepage.shared.domain.model.FeaturedPlaylists
import com.spotify.app.feature_homepage.shared.domain.use_case.FetchFeaturedPlaylistsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import org.mobilenativefoundation.store.store5.Fetcher
import org.mobilenativefoundation.store.store5.StoreBuilder
import org.mobilenativefoundation.store.store5.StoreReadRequest

class FetchFeaturedPlaylistsUseCaseImpl(
    private val homePageRepository: HomePageRepository
) : FetchFeaturedPlaylistsUseCase {

    companion object {
        private const val CACHE_KEY = "feature-playlists"
    }

    private val store =
        StoreBuilder.from<String, RestClientResult<FeaturedPlaylists>>(
            fetcher = Fetcher.of {
                homePageRepository.fetchFeaturedPlaylists()
                    .mapFromDTO { FeaturePlaylistsDtoMapper.asDomain(it!!) }
            }
//            ,
//            sourceOfTruth = SourceOfTruth.of(
//                reader = {
//                    flowOf(null)
//                },
//                writer = { key, input ->
//
//                },
//                delete = {
//
//                }
//            )
        )
            .build()

    override suspend fun fetchFeaturedPlaylists(): Flow<RestClientResult<FeaturedPlaylists?>> {
        return store.stream(StoreReadRequest.cached(key = CACHE_KEY, refresh = false))
            .flowOn(Dispatchers.IO)
            .mapStoreResponseToRestClientResult()
    }

}