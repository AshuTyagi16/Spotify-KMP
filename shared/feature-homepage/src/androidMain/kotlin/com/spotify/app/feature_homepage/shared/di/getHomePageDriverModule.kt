package com.spotify.app.feature_homepage.shared.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.spotify.app.feature_homepage.shared.HomePageDatabase
import com.spotify.app.feature_homepage.shared.ui.HomePageViewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel

actual fun getHomePlatformModule(): Module = module {
    single<SqlDriver> {
        AndroidSqliteDriver(HomePageDatabase.Schema, get(), "homepage.db")
    }
    viewModel { HomePageViewModel(get(), get()) }
}