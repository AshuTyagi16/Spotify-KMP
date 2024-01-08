package com.spotify.app.feature_homepage.shared.domain.use_case.impl

import com.spotify.app.feature_homepage.shared.data.repository.HomePageRepository
import com.spotify.app.feature_homepage.shared.domain.use_case.FetchFeaturedPlaylistsUseCase

internal class FetchFeaturedPlaylistsUseCaseImpl(
    private val homePageRepository: HomePageRepository
) : FetchFeaturedPlaylistsUseCase {

    override suspend fun fetchFeaturedPlaylists() = homePageRepository.fetchFeaturedPlaylists()

}