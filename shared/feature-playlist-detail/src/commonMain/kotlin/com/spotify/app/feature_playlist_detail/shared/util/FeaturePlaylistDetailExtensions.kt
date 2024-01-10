package com.spotify.app.feature_playlist_detail.shared.util

import com.spotify.app.feature_playlist_detail.shared.data.dto.PlaylistDetailDTO
import com.spotify.app.feature_playlist_detail.shared.domain.model.FetchPlaylistDetailRequest
import com.spotify.app.feature_playlist_detail.shared.domain.model.PlaylistDetailItem
import com.spotify.app.featureplaylistdetail.shared.PlaylistDetailItemEntity

fun PlaylistDetailDTO.toPlaylistDetailItemList(
    fetchPlaylistDetailRequest: FetchPlaylistDetailRequest
): List<PlaylistDetailItem> {
    return this.items.map {
        PlaylistDetailItem(
            id = it.track.id,
            playlistId = fetchPlaylistDetailRequest.playlistId,
            trackName = it.track.name,
            artists = it.track.album.artists.joinToString(separator = ",") { it.name },
            image = it.track.album.images.firstOrNull()?.url.orEmpty(),
            limit = fetchPlaylistDetailRequest.limit,
            offset = fetchPlaylistDetailRequest.offset,
            totalItemCount = this.total
        )
    }
}

fun PlaylistDetailItemEntity.toPlaylistDetailItem(): PlaylistDetailItem {
    return PlaylistDetailItem(
        id = this.id,
        playlistId = this.playlistId,
        trackName = this.trackName,
        artists = this.artists,
        image = this.image,
        limit = this.limitValue,
        offset = this.offsetValue,
        totalItemCount = this.total
    )
}

fun List<PlaylistDetailItemEntity>.toPlaylistDetailItemList(): List<PlaylistDetailItem> {
    return this.map { it.toPlaylistDetailItem() }
}