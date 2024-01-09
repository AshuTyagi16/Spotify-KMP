package com.spotify.app.feature_homepage.shared.data.network

import com.spotify.app.core_network.shared.impl.data.base.BaseDataSource
import com.spotify.app.feature_homepage.shared.data.dto.album.FeaturedAlbumsDTO
import com.spotify.app.feature_homepage.shared.data.dto.playlist.FeaturedPlaylistsDTO
import com.spotify.app.feature_homepage.shared.util.FeatureHomePageConstants.Endpoints
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.url

internal class HomePageRemoteDataSource(
    private val client: HttpClient
) : BaseDataSource() {

    suspend fun fetchFeaturedPlaylist() = getResult<FeaturedPlaylistsDTO> {
        client.get {
            url(Endpoints.FETCH_FEATURED_PLAYLISTS)
        }
    }

    suspend fun fetchFeaturedAlbums() = getResult<FeaturedAlbumsDTO> {
        client.get {
            url(Endpoints.FETCH_FEATURED_ALBUMS)
        }
    }

}