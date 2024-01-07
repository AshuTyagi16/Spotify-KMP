package com.spotify.app.feature_homepage.shared.data.repository

import com.spotify.app.core_network.shared.impl.data.model.RestClientResult
import com.spotify.app.feature_homepage.shared.data.dto.FeaturedPlaylistsDTO

interface HomePageRepository {

    suspend fun fetchFeaturedPlaylists(): RestClientResult<FeaturedPlaylistsDTO>

}