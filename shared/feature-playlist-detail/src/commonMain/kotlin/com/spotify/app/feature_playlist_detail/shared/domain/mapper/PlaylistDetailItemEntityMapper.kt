package com.spotify.app.feature_playlist_detail.shared.domain.mapper

import com.spotify.app.core_base.shared.domain.mapper.EntityMapper
import com.spotify.app.core_base.shared.domain.model.AlbumItem
import com.spotify.app.feature_playlist_detail.shared.domain.model.FetchPlaylistDetailRequest
import com.spotify.app.feature_playlist_detail.shared.domain.model.PlaylistDetailItem
import com.spotify.app.feature_playlist_detail.shared.domain.model.Track
import com.spotify.app.featureplaylistdetail.shared.PlaylistDetailItemEntity

object PlaylistDetailItemEntityMapper : EntityMapper<PlaylistDetailItem, PlaylistDetailItemEntity> {
    override fun asEntity(domain: PlaylistDetailItem): PlaylistDetailItemEntity {
        return PlaylistDetailItemEntity(
            id = domain.track.id,
            playlistId = domain.fetchPlaylistDetailRequest.playlistId,
            trackName = domain.track.name,
            artists = domain.track.album.artists,
            image = domain.track.album.image,
            limitValue = domain.fetchPlaylistDetailRequest.limit,
            offsetValue = domain.fetchPlaylistDetailRequest.offset,
            total = domain.totalItemCount
        )
    }

    override fun asDomain(entity: PlaylistDetailItemEntity): PlaylistDetailItem {
        return PlaylistDetailItem(
            track = Track(
                album = AlbumItem(
                    id = "",
                    name = "",
                    image = entity.image,
                    releaseDate = "",
                    trackCount = 0,
                    artists = entity.artists
                ),
                durationMs = 0,
                episode = false,
                explicit = false,
                id = entity.id,
                name = entity.trackName,
                popularity = 0,
                previewUrl = null,
                track = true,
                trackNumber = 0
            ),
            fetchPlaylistDetailRequest = FetchPlaylistDetailRequest(
                playlistId = entity.playlistId,
                limit = entity.limitValue,
                offset = entity.offsetValue
            ),
            totalItemCount = entity.total
        )
    }

}