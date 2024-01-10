package com.spotify.app.feature_homepage.shared.di

import com.spotify.app.feature_homepage.shared.HomePageDatabase
import com.spotify.app.feature_homepage.shared.data.local.HomePageLocalDataSource
import com.spotify.app.feature_homepage.shared.data.network.HomePageRemoteDataSource
import com.spotify.app.feature_homepage.shared.data.repository.HomePageRepository
import com.spotify.app.feature_homepage.shared.domain.repository.HomePageRepositoryImpl
import com.spotify.app.feature_homepage.shared.domain.use_case.FetchFeaturedAlbumsUseCase
import com.spotify.app.feature_homepage.shared.domain.use_case.FetchFeaturedPlaylistsUseCase
import com.spotify.app.feature_homepage.shared.ui.HomePageViewModel
import com.spotify.app.core_base.shared.util.CacheExpirationUtil
import com.spotify.app.feature_homepage.shared.util.FeatureHomePageConstants
import org.koin.core.qualifier.named
import org.koin.dsl.module

val featureHomePageModule = module {
    single {
        HomePageDatabase(get(named(FeatureHomePageConstants.QUALIFIER_HOMEPAGE_DATABASE)))
    }
    single {
        HomePageLocalDataSource(get())
    }
    single {
        HomePageRemoteDataSource(get())
    }
    single {
        CacheExpirationUtil(get())
    }
    single<HomePageRepository> {
        HomePageRepositoryImpl(get(), get(), get())
    }
    single {
        FetchFeaturedPlaylistsUseCase(get<HomePageRepository>()::fetchFeaturedPlaylists)
    }
    single {
        FetchFeaturedAlbumsUseCase(get<HomePageRepository>()::fetchFeaturedAlbums)
    }
    single {
        HomePageViewModel(get(), get())
    }

}