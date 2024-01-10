package com.spotify.app.feature_homepage.shared.util

import com.spotify.app.core_base.shared.domain.model.AlbumItem
import com.spotify.app.core_base.shared.util.toAlbumItem
import com.spotify.app.core_base.shared.util.toAlbumItemList
import com.spotify.app.feature_homepage.shared.data.dto.album.FeaturedAlbumsDTO
import com.spotify.app.feature_homepage.shared.data.dto.playlist.FeaturedPlaylistsDTO
import com.spotify.app.feature_homepage.shared.domain.model.playlist.PlaylistItem
import com.spotify.app.featurehomepage.shared.AlbumItemEntity
import com.spotify.app.featurehomepage.shared.PlaylistItemEntitity


fun FeaturedPlaylistsDTO.toPlaylistItemList(): List<PlaylistItem> {
    return this.playlists.items.map {
        PlaylistItem(
            id = it.id,
            description = it.description,
            name = it.name,
            image = it.images.firstOrNull()?.url.orEmpty(),
            trackCount = it.tracks.total
        )
    }
}

fun PlaylistItemEntitity.toPlaylistItem(): PlaylistItem {
    return PlaylistItem(
        id = this.id,
        description = this.description,
        name = this.name,
        image = this.image,
        trackCount = this.trackCount
    )
}

fun List<PlaylistItemEntitity>.toPlaylistItemList(): List<PlaylistItem> {
    return this.map { it.toPlaylistItem() }
}

fun FeaturedAlbumsDTO.toAlbumItemList(): List<AlbumItem> {
    return this.albums.toAlbumItemList()
}

fun AlbumItemEntity.toAlbumItem(): AlbumItem {
    return AlbumItem(
        id = this.id,
        name = this.name,
        image = this.image,
        releaseDate = this.releaseDate,
        trackCount = this.trackCount,
        artists = this.artists
    )
}

fun List<AlbumItemEntity>.toAlbumItemList(): List<AlbumItem> {
    return this.map { it.toAlbumItem() }
}