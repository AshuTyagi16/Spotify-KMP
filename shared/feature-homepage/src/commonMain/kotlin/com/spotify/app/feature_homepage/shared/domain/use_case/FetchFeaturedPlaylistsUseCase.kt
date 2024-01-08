package com.spotify.app.feature_homepage.shared.domain.use_case

import com.spotify.app.core_network.shared.impl.data.model.RestClientResult
import com.spotify.app.feature_homepage.shared.domain.model.playlist.FeaturedPlaylists
import com.spotify.app.feature_homepage.shared.domain.model.playlist.PlaylistItem
import kotlinx.coroutines.flow.Flow

interface FetchFeaturedPlaylistsUseCase {

    suspend fun fetchFeaturedPlaylists(): Flow<RestClientResult<List<PlaylistItem>>>

}