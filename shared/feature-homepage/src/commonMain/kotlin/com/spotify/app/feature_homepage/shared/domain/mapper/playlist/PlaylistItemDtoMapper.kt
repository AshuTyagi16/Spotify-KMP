package com.spotify.app.feature_homepage.shared.domain.mapper.playlist

import com.spotify.app.core_base.shared.data.base.DtoMapper
import com.spotify.app.core_base.shared.data.dto.ImageDTO
import com.spotify.app.feature_homepage.shared.data.dto.playlist.PlaylistItemDTO
import com.spotify.app.feature_homepage.shared.data.dto.playlist.TracksInfoDto
import com.spotify.app.feature_homepage.shared.domain.model.playlist.PlaylistItem

internal object PlaylistItemDtoMapper : DtoMapper<PlaylistItem, PlaylistItemDTO> {
    override fun asDto(domain: PlaylistItem): PlaylistItemDTO {
        return PlaylistItemDTO(
            description = domain.description,
            id = domain.id,
            images = listOf(ImageDTO(domain.image)),
            name = domain.name,
            tracks = TracksInfoDto(total = domain.trackCount)
        )
    }

    override fun asDomain(dto: PlaylistItemDTO): PlaylistItem {
        return PlaylistItem(
            description = dto.description,
            id = dto.id,
            image = dto.images.firstOrNull()?.url.orEmpty(),
            name = dto.name,
            trackCount = dto.tracks.total
        )
    }
}