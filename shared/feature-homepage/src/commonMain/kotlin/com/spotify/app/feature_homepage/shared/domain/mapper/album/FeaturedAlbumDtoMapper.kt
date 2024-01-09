package com.spotify.app.feature_homepage.shared.domain.mapper.album

import com.spotify.app.core_base.shared.data.base.DtoMapper
import com.spotify.app.core_base.shared.domain.mapper.AlbumDtoMapper
import com.spotify.app.feature_homepage.shared.data.dto.album.FeaturedAlbumsDTO
import com.spotify.app.feature_homepage.shared.domain.model.album.FeaturedAlbums

object FeaturedAlbumDtoMapper : DtoMapper<FeaturedAlbums, FeaturedAlbumsDTO> {

    override fun asDto(domain: FeaturedAlbums): FeaturedAlbumsDTO {
        return FeaturedAlbumsDTO(
            albums = AlbumDtoMapper.asDto(domain.albums)
        )
    }

    override fun asDomain(dto: FeaturedAlbumsDTO): FeaturedAlbums {
        return FeaturedAlbums(
            albums = AlbumDtoMapper.asDomain(dto.albums)
        )
    }

}