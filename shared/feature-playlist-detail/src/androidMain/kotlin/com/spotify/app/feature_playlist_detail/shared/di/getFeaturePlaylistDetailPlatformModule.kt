package com.spotify.app.feature_playlist_detail.shared.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.spotify.app.feature_playlist_detail.shared.PlaylistDetailDatabase
import com.spotify.app.feature_playlist_detail.shared.ui.PlaylistDetailViewModel
import com.spotify.app.feature_playlist_detail.shared.util.FeaturePlaylistDetailConstants
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

actual fun getFeaturePlaylistDetailPlatformModule(): Module = module {
    single<SqlDriver>(named(FeaturePlaylistDetailConstants.QUALIFIER_PLAYLIST_DATABASE)) {
        AndroidSqliteDriver(
            PlaylistDetailDatabase.Schema,
            get(),
            FeaturePlaylistDetailConstants.PLAYLIST_DATABASE_NAME
        )
    }
    viewModel { PlaylistDetailViewModel(get()) }
}
