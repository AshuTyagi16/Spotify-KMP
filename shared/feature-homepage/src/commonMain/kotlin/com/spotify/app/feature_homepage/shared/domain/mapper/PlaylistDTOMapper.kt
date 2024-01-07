package com.spotify.app.feature_homepage.shared.domain.mapper

import com.spotify.app.core_base.shared.data.base.DtoMapper
import com.spotify.app.feature_homepage.shared.data.dto.PlaylistDTO
import com.spotify.app.feature_homepage.shared.domain.model.Playlist

internal object PlaylistDTOMapper : DtoMapper<Playlist, PlaylistDTO> {
    override fun asDto(domain: Playlist): PlaylistDTO {
        return PlaylistDTO(
            items = domain.items.map { PlaylistItemDtoMapper.asDto(it) },
            total = domain.total
        )
    }

    override fun asDomain(dto: PlaylistDTO): Playlist {
        return Playlist(
            items = dto.items.map { PlaylistItemDtoMapper.asDomain(it) },
            total = dto.total
        )
    }

}