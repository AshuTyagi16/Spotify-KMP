package com.spotify.app.feature_playlist_detail.shared.di

import com.spotify.app.feature_playlist_detail.shared.ui.PlaylistDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun getFeaturePlaylistDetailPlatformModule(): Module = module {
    viewModel { PlaylistDetailViewModel(get()) }
}
