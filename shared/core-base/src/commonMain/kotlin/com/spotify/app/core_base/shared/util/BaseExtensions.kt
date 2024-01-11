package com.spotify.app.core_base.shared.util

import com.spotify.app.core_base.shared.data.dto.AlbumDTO
import com.spotify.app.core_base.shared.data.dto.AlbumItemDTO
import com.spotify.app.core_base.shared.domain.model.AlbumItem

fun AlbumDTO.toAlbumItemList(): List<AlbumItem> {
    return this.items?.map { it.toAlbumItem() }.orEmpty()
}

fun AlbumItemDTO.toAlbumItem(): AlbumItem {
    return AlbumItem(
        id = this.id,
        name = this.name,
        image = this.images?.firstOrNull()?.url.orEmpty(),
        releaseDate = this.releaseDate,
        trackCount = this.totalTracks,
        artists = this.artists.joinToString(separator = ",") { it.name }
    )
}