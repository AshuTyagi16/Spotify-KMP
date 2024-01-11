package com.spotify.app.feature_album_detail.shared.util

import com.spotify.app.feature_album_detail.shared.data.dto.AlbumDetailResponseDTO
import com.spotify.app.feature_album_detail.shared.domain.model.AlbumDetailItem
import com.spotify.app.feature_album_detail.shared.domain.model.FetchAlbumDetailRequest
import com.spotify.app.featurealbumdetail.shared.AlbumDetailItemEntity

fun AlbumDetailItemEntity.toAlbumDetailItem(): AlbumDetailItem {
    return AlbumDetailItem(
        id = this.id,
        albumId = this.albumId,
        trackName = this.trackName,
        artists = this.artists,
        limitValue = this.limitValue,
        offsetValue = this.offsetValue,
        total = this.total
    )
}

fun AlbumDetailResponseDTO.toAlbumDetailItemList(fetchAlbumDetailRequest: FetchAlbumDetailRequest): List<AlbumDetailItem> {
    return this.items.map {
        AlbumDetailItem(
            id = it.id,
            albumId = fetchAlbumDetailRequest.albumId,
            trackName = it.name,
            artists = it.artists.joinToString(separator = ",") { it.name },
            limitValue = fetchAlbumDetailRequest.limit,
            offsetValue = fetchAlbumDetailRequest.offset,
            total = this.total
        )
    }
}

fun List<AlbumDetailItemEntity>.toAlbumDetailList(): List<AlbumDetailItem> {
    return this.map { it.toAlbumDetailItem() }
}