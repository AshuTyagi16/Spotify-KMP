package com.spotify.app.feature_homepage.shared.ui

import com.spotify.app.core_base.shared.models.ViewModel
import com.spotify.app.core_network.shared.impl.data.model.RestClientResult
import com.spotify.app.core_base.shared.domain.model.AlbumItem
import com.spotify.app.feature_homepage.shared.domain.model.playlist.PlaylistItem
import com.spotify.app.feature_homepage.shared.domain.use_case.FetchFeaturedAlbumsUseCase
import com.spotify.app.feature_homepage.shared.domain.use_case.FetchFeaturedPlaylistsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest

class HomePageViewModel(
    private val fetchFeaturedPlaylistsUseCase: FetchFeaturedPlaylistsUseCase,
    private val fetchFeaturedAlbumsUseCase: FetchFeaturedAlbumsUseCase
) : ViewModel() {

    val featuredPlaylistData =
        MutableStateFlow<RestClientResult<List<PlaylistItem>>>(RestClientResult.idle())
    val featuredAlbumsData =
        MutableStateFlow<RestClientResult<List<AlbumItem>>>(RestClientResult.idle())

    suspend fun fetchFeaturedPlaylists() {
        fetchFeaturedPlaylistsUseCase.invoke().collectLatest {
            featuredPlaylistData.emit(it)
        }
    }

    suspend fun fetchFeaturedAlbums() {
        fetchFeaturedAlbumsUseCase.invoke().collectLatest {
            featuredAlbumsData.emit(it)
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}