package com.spotify.app.feature_homepage.shared.di

import com.spotify.app.feature_homepage.shared.HomePageDatabase
import com.spotify.app.feature_homepage.shared.data.local.HomePageLocalDataSource
import com.spotify.app.feature_homepage.shared.data.network.HomePageRemoteDataSource
import com.spotify.app.feature_homepage.shared.data.repository.HomePageRepository
import com.spotify.app.feature_homepage.shared.domain.repository.HomePageRepositoryImpl
import com.spotify.app.feature_homepage.shared.domain.use_case.FetchFeaturedPlaylistsUseCase
import com.spotify.app.feature_homepage.shared.domain.use_case.impl.FetchFeaturedPlaylistsUseCaseImpl
import com.spotify.app.feature_homepage.shared.ui.HomePageViewModel
import com.spotify.app.feature_homepage.shared.util.CacheExpirationUtil
import org.koin.dsl.module

val featureHomePageModule = module {
    single {
        HomePageDatabase(get())
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
    single<FetchFeaturedPlaylistsUseCase> {
        FetchFeaturedPlaylistsUseCaseImpl(get())
    }
    single {
        HomePageViewModel(get(), scope = it.get())
    }

}