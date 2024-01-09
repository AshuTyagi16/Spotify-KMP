package com.spotify.app.feature_homepage.shared.domain.mapper.album

import com.spotify.app.core_base.shared.data.base.DtoMapper
import com.spotify.app.feature_homepage.shared.data.dto.album.ArtistDTO
import com.spotify.app.feature_homepage.shared.domain.model.album.Artist

object ArtistDtoMapper : DtoMapper<Artist, ArtistDTO> {
    override fun asDto(domain: Artist): ArtistDTO {
        return ArtistDTO(
            name = domain.name
        )
    }

    override fun asDomain(dto: ArtistDTO): Artist {
        return Artist(
            name = dto.name
        )
    }

}