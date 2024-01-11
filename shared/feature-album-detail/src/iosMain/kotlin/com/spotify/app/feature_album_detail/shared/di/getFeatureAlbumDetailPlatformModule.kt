package com.spotify.app.feature_album_detail.shared.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.spotify.app.feature_album_detail.shared.AlbumDetailDatabase
import com.spotify.app.feature_album_detail.shared.util.FeatureAlbumDetailConstants
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

actual fun getFeatureAlbumDetailPlatformModule(): Module = module {
    single<SqlDriver>(named(FeatureAlbumDetailConstants.QUALIFIER_ALBUM_DATABASE)) {
        NativeSqliteDriver(
            AlbumDetailDatabase.Schema,
            FeatureAlbumDetailConstants.ALBUM_DATABASE_NAME
        )
    }
//    single { PlaylistDetailViewModel(get()) }
}
