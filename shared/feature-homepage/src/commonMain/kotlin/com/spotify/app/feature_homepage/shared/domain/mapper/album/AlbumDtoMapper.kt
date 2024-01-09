package com.spotify.app.feature_homepage.shared.domain.mapper.album

import com.spotify.app.core_base.shared.data.base.DtoMapper
import com.spotify.app.feature_homepage.shared.data.dto.album.AlbumDTO
import com.spotify.app.feature_homepage.shared.domain.model.album.Album

object AlbumDtoMapper : DtoMapper<Album, AlbumDTO> {
    override fun asDto(domain: Album): AlbumDTO {
        return AlbumDTO(
            items = domain.items.map { AlbumItemDtoMapper.asDto(it) },
            limit = domain.limit,
            total = domain.total
        )
    }

    override fun asDomain(dto: AlbumDTO): Album {
        return Album(
            items = dto.items.map { AlbumItemDtoMapper.asDomain(it) },
            limit = dto.limit,
            total = dto.total
        )
    }

}