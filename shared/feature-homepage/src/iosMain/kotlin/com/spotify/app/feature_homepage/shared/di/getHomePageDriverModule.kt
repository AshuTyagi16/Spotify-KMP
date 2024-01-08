package com.spotify.app.feature_homepage.shared.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.spotify.app.feature_homepage.shared.HomePageDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun getHomePageDriverModule(): Module = module {
    single<SqlDriver> {
        NativeSqliteDriver(HomePageDatabase.Schema, "homepage.db")
    }
}