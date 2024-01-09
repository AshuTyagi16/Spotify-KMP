package com.spotify.app.feature_playlist_detail.shared.di

import com.spotify.app.feature_playlist_detail.shared.data.network.PlaylistDetailDataSource
import com.spotify.app.feature_playlist_detail.shared.data.repository.PlaylistDetailRepository
import com.spotify.app.feature_playlist_detail.shared.domain.repository.PlaylistDetailRepositoryImpl
import com.spotify.app.feature_playlist_detail.shared.domain.use_case.FetchPlaylistDetailUseCase
import org.koin.dsl.module

val featurePlaylistDetailModule = module {
    single {
        PlaylistDetailDataSource(get())
    }
    single<PlaylistDetailRepository> {
        PlaylistDetailRepositoryImpl(get())
    }
    single {
        FetchPlaylistDetailUseCase(get<PlaylistDetailRepository>()::fetchPlaylistDetail)
    }
}