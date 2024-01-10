package com.spotify.app.feature_homepage.shared.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.spotify.app.feature_homepage.shared.HomePageDatabase
import com.spotify.app.feature_homepage.shared.ui.HomePageViewModel
import com.spotify.app.feature_homepage.shared.util.FeatureHomePageConstants
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

actual fun getHomePlatformModule(): Module = module {
    single<SqlDriver>(named(FeatureHomePageConstants.QUALIFIER_HOMEPAGE_DATABASE)) {
        NativeSqliteDriver(
            HomePageDatabase.Schema,
            FeatureHomePageConstants.HOMEPAGE_DATABASE_NAME
        )
    }

    single { HomePageViewModel(get(), get()) }
}