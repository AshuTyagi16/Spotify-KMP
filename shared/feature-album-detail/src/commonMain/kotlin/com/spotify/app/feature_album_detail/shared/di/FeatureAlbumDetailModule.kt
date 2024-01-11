package com.spotify.app.feature_album_detail.shared.di

import com.spotify.app.feature_album_detail.shared.AlbumDetailDatabase
import com.spotify.app.feature_album_detail.shared.data.local.AlbumDetailLocalDataSource
import com.spotify.app.feature_album_detail.shared.data.network.AlbumDetailRemoteDataSource
import com.spotify.app.feature_album_detail.shared.data.repository.AlbumDetailRepository
import com.spotify.app.feature_album_detail.shared.domain.paging_source.AlbumDetailPagingSource
import com.spotify.app.feature_album_detail.shared.domain.repository.AlbumDetailRepositoryImpl
import com.spotify.app.feature_album_detail.shared.domain.use_case.FetchAlbumDetailUseCase
import com.spotify.app.feature_album_detail.shared.domain.use_case.impl.FetchAlbumDetailUseCaseImpl
import com.spotify.app.feature_album_detail.shared.util.FeatureAlbumDetailConstants
import org.koin.core.qualifier.named
import org.koin.dsl.module

val featureAlbumDetailModule = module {
    single {
        AlbumDetailDatabase(get(named(FeatureAlbumDetailConstants.QUALIFIER_ALBUM_DATABASE)))
    }
    single {
        AlbumDetailLocalDataSource(get())
    }
    single {
        AlbumDetailRemoteDataSource(get())
    }
    single<AlbumDetailRepository> {
        AlbumDetailRepositoryImpl(get(), get(), get())
    }
    single<FetchAlbumDetailUseCase> {
        FetchAlbumDetailUseCaseImpl()
    }
    factory {
        AlbumDetailPagingSource(albumId = it.get(), get())
    }
}