package com.spotify.app.feature_playlist_detail.shared.domain.mapper

import com.spotify.app.core_base.shared.data.base.DtoMapper
import com.spotify.app.feature_playlist_detail.shared.data.dto.PlaylistDetailDTO
import com.spotify.app.feature_playlist_detail.shared.domain.model.FetchPlaylistDetailRequest
import com.spotify.app.feature_playlist_detail.shared.domain.model.PlaylistDetail

class PlaylistDetailDtoMapper(
    private val fetchPlaylistDetailRequest: FetchPlaylistDetailRequest
) : DtoMapper<PlaylistDetail, PlaylistDetailDTO> {


    override fun asDto(domain: PlaylistDetail): PlaylistDetailDTO {
        val playlistDetailItemDtoMapper =
            PlaylistDetailItemDtoMapper(fetchPlaylistDetailRequest, domain.total)
        return PlaylistDetailDTO(
            items = domain.items.map { playlistDetailItemDtoMapper.asDto(it) },
            limit = domain.limit,
            offset = domain.offset,
            total = domain.total
        )
    }

    override fun asDomain(dto: PlaylistDetailDTO): PlaylistDetail {
        val playlistDetailItemDtoMapper =
            PlaylistDetailItemDtoMapper(fetchPlaylistDetailRequest, dto.total)
        return PlaylistDetail(
            items = dto.items.map { playlistDetailItemDtoMapper.asDomain(it) },
            limit = dto.limit,
            offset = dto.offset,
            total = dto.total
        )
    }

}