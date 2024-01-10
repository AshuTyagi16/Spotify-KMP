package com.spotify.app.feature_playlist_detail.shared.di

import com.spotify.app.feature_playlist_detail.shared.PlaylistDetailDatabase
import com.spotify.app.feature_playlist_detail.shared.data.network.PlaylistDetailLocalDataSource
import com.spotify.app.feature_playlist_detail.shared.data.network.PlaylistDetailRemoteDataSource
import com.spotify.app.feature_playlist_detail.shared.data.repository.PlaylistDetailRepository
import com.spotify.app.feature_playlist_detail.shared.domain.paging_source.PlaylistDetailPagingSource
import com.spotify.app.feature_playlist_detail.shared.domain.repository.PlaylistDetailRepositoryImpl
import com.spotify.app.feature_playlist_detail.shared.domain.use_case.FetchPlaylistDetailUseCase
import com.spotify.app.feature_playlist_detail.shared.domain.use_case.impl.FetchPlaylistDetailUseCaseImpl
import com.spotify.app.feature_playlist_detail.shared.util.FeaturePlaylistDetailConstants
import org.koin.core.qualifier.named
import org.koin.dsl.module

val featurePlaylistDetailModule = module {
    single {
        PlaylistDetailDatabase(get(named(FeaturePlaylistDetailConstants.QUALIFIER_PLAYLIST_DATABASE)))
    }
    single {
        PlaylistDetailLocalDataSource(get())
    }
    single {
        PlaylistDetailRemoteDataSource(get())
    }
    single<PlaylistDetailRepository> {
        PlaylistDetailRepositoryImpl(get(), get(), get())
    }
    single<FetchPlaylistDetailUseCase> {
        FetchPlaylistDetailUseCaseImpl()
    }
    factory {
        PlaylistDetailPagingSource(playlistId = it.get(), get())
    }
}