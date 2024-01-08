package com.spotify.app.feature_homepage.shared.data.repository

import com.spotify.app.core_network.shared.impl.data.model.RestClientResult
import com.spotify.app.feature_homepage.shared.domain.model.playlist.PlaylistItem
import kotlinx.coroutines.flow.Flow

interface HomePageRepository {

    suspend fun fetchFeaturedPlaylists(): Flow<RestClientResult<List<PlaylistItem>>>

}