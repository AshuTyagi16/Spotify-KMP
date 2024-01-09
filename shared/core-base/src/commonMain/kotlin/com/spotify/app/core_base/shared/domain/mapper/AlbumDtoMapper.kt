package com.spotify.app.core_base.shared.domain.mapper

import com.spotify.app.core_base.shared.data.base.DtoMapper
import com.spotify.app.core_base.shared.data.dto.AlbumDTO
import com.spotify.app.core_base.shared.domain.model.Album

object AlbumDtoMapper : DtoMapper<Album, AlbumDTO> {
    override fun asDto(domain: Album): AlbumDTO {
        return AlbumDTO(
            items = domain.items?.map { AlbumItemDtoMapper.asDto(it) },
            limit = domain.limit,
            total = domain.total
        )
    }

    override fun asDomain(dto: AlbumDTO): Album {
        return Album(
            items = dto.items?.map { AlbumItemDtoMapper.asDomain(it) },
            limit = dto.limit,
            total = dto.total
        )
    }

}