package com.spotify.app.core_base.shared.domain.mapper

import com.spotify.app.core_base.shared.data.base.DtoMapper
import com.spotify.app.core_base.shared.data.dto.ImageDTO
import com.spotify.app.core_base.shared.data.dto.AlbumItemDTO
import com.spotify.app.core_base.shared.data.dto.ArtistDTO
import com.spotify.app.core_base.shared.domain.model.AlbumItem

object AlbumItemDtoMapper : DtoMapper<AlbumItem, AlbumItemDTO> {
    override fun asDto(domain: AlbumItem): AlbumItemDTO {
        return AlbumItemDTO(
            artists = domain.artists.split(",").map { ArtistDTO(name = it) },
            id = domain.id,
            images = listOf(ImageDTO(url = domain.image)),
            name = domain.name,
            releaseDate = domain.releaseDate,
            totalTracks = domain.trackCount
        )
    }

    override fun asDomain(dto: AlbumItemDTO): AlbumItem {
        return AlbumItem(
            artists = dto.artists.joinToString(separator = ",") { it.name },
            id = dto.id,
            image = dto.images.firstOrNull()?.url.orEmpty(),
            name = dto.name,
            releaseDate = dto.releaseDate,
            trackCount = dto.totalTracks
        )
    }

}