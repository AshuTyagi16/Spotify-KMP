package com.spotify.app.feature_homepage.shared.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.spotify.app.feature_homepage.shared.HomePageDatabase
import com.spotify.app.feature_homepage.shared.ui.HomePageViewModel
import com.spotify.app.feature_homepage.shared.util.FeatureHomePageConstants
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named

actual fun getHomePlatformModule(): Module = module {
    single<SqlDriver>(named(FeatureHomePageConstants.QUALIFIER_HOMEPAGE_DATABASE)) {
        AndroidSqliteDriver(
            HomePageDatabase.Schema,
            get(),
            FeatureHomePageConstants.HOMEPAGE_DATABASE_NAME
        )
    }
    viewModel { HomePageViewModel(get(), get()) }
}