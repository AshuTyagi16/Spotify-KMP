package com.spotify.app.feature_homepage.shared.ui

import com.spotify.app.core_network.shared.impl.data.model.RestClientResult
import com.spotify.app.feature_homepage.shared.domain.model.playlist.FeaturedPlaylists
import com.spotify.app.feature_homepage.shared.domain.model.playlist.PlaylistItem
import com.spotify.app.feature_homepage.shared.domain.use_case.FetchFeaturedPlaylistsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomePageViewModel constructor(
    private val featuredPlaylistsUseCase: FetchFeaturedPlaylistsUseCase,
    scope: CoroutineScope?
) {

    private val viewModelScope = scope ?: CoroutineScope(Dispatchers.Main)

    val data = MutableStateFlow<RestClientResult<List<PlaylistItem>>>(RestClientResult.idle())

    fun fetchFeaturedPlaylists() {
        viewModelScope.launch {
            featuredPlaylistsUseCase.fetchFeaturedPlaylists().collectLatest {
                data.emit(it)
            }
        }
    }
}