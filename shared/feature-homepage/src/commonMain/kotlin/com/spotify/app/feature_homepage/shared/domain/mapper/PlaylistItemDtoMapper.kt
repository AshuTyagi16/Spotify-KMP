package com.spotify.app.feature_homepage.shared.domain.mapper

import com.spotify.app.core_base.shared.data.base.DtoMapper
import com.spotify.app.core_base.shared.domain.mapper.ImageDtoMapper
import com.spotify.app.feature_homepage.shared.data.dto.PlaylistItemDTO
import com.spotify.app.feature_homepage.shared.domain.model.PlaylistItem

internal object PlaylistItemDtoMapper : DtoMapper<PlaylistItem, PlaylistItemDTO> {
    override fun asDto(domain: PlaylistItem): PlaylistItemDTO {
        return PlaylistItemDTO(
            description = domain.description,
            id = domain.id,
            images = domain.images.map { ImageDtoMapper.asDto(it) },
            name = domain.name,
            tracks = TrackInfoDtoMapper.asDto(domain.tracks),
            type = domain.type,
            uri = domain.uri
        )
    }

    override fun asDomain(dto: PlaylistItemDTO): PlaylistItem {
        return PlaylistItem(
            description = dto.description,
            id = dto.id,
            images = dto.images.map { ImageDtoMapper.asDomain(it) },
            name = dto.name,
            tracks = TrackInfoDtoMapper.asDomain(dto.tracks),
            type = dto.type,
            uri = dto.uri
        )
    }
}