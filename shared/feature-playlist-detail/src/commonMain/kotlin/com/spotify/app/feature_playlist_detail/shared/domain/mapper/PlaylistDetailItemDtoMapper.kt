package com.spotify.app.feature_playlist_detail.shared.domain.mapper

import com.spotify.app.core_base.shared.data.base.DtoMapper
import com.spotify.app.feature_playlist_detail.shared.data.dto.PlaylistDetailItemDTO
import com.spotify.app.feature_playlist_detail.shared.domain.model.FetchPlaylistDetailRequest
import com.spotify.app.feature_playlist_detail.shared.domain.model.PlaylistDetailItem

class PlaylistDetailItemDtoMapper(
    private val fetchPlaylistDetailRequest: FetchPlaylistDetailRequest,
    private val totalItemCount: Long
) : DtoMapper<PlaylistDetailItem, PlaylistDetailItemDTO> {
    override fun asDto(domain: PlaylistDetailItem): PlaylistDetailItemDTO {
        return PlaylistDetailItemDTO(
            track = TrackDtoMapper.asDto(domain.track)
        )
    }

    override fun asDomain(dto: PlaylistDetailItemDTO): PlaylistDetailItem {
        return PlaylistDetailItem(
            track = TrackDtoMapper.asDomain(dto.track),
            fetchPlaylistDetailRequest = fetchPlaylistDetailRequest,
            totalItemCount = totalItemCount
        )
    }

}