package com.spotify.app.feature_homepage.shared.domain.repository

import com.spotify.app.feature_homepage.shared.data.network.HomePageDataSource
import com.spotify.app.feature_homepage.shared.data.repository.HomePageRepository

class HomePageRepositoryImpl(
    private val homePageDataSource: HomePageDataSource
) : HomePageRepository {

    override suspend fun fetchFeaturedPlaylists() = homePageDataSource.fetchFeaturedPlaylist()

}