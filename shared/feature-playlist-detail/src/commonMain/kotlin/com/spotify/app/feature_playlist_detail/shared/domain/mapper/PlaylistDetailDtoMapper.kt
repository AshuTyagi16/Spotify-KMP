package com.spotify.app.feature_playlist_detail.shared.domain.mapper

import com.spotify.app.core_base.shared.data.base.DtoMapper
import com.spotify.app.feature_playlist_detail.shared.data.dto.PlaylistDetailDTO
import com.spotify.app.feature_playlist_detail.shared.domain.model.PlaylistDetail

object PlaylistDetailDtoMapper : DtoMapper<PlaylistDetail, PlaylistDetailDTO> {
    override fun asDto(domain: PlaylistDetail): PlaylistDetailDTO {
        return PlaylistDetailDTO(
            items = domain.items.map { PlaylistDetailItemDtoMapper.asDto(it) },
            limit = domain.limit,
            offset = domain.offset,
            total = domain.total
        )
    }

    override fun asDomain(dto: PlaylistDetailDTO): PlaylistDetail {
        return PlaylistDetail(
            items = dto.items.map { PlaylistDetailItemDtoMapper.asDomain(it) },
            limit = dto.limit,
            offset = dto.offset,
            total = dto.total
        )
    }

}