package com.spotify.app.feature_homepage.shared.di

import com.spotify.app.feature_homepage.shared.data.network.HomePageDataSource
import com.spotify.app.feature_homepage.shared.data.repository.HomePageRepository
import com.spotify.app.feature_homepage.shared.domain.repository.HomePageRepositoryImpl
import com.spotify.app.feature_homepage.shared.domain.use_case.FetchFeaturedPlaylistsUseCase
import com.spotify.app.feature_homepage.shared.domain.use_case.impl.FetchFeaturedPlaylistsUseCaseImpl
import org.koin.dsl.module

val featureHomePageModule = module {
    single {
        HomePageDataSource(get())
    }
    single<HomePageRepository> {
        HomePageRepositoryImpl(get())
    }
    single<FetchFeaturedPlaylistsUseCase> {
        FetchFeaturedPlaylistsUseCaseImpl(get())
    }
}