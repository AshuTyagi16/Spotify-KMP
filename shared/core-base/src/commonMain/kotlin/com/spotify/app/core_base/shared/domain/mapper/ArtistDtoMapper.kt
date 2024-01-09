package com.spotify.app.core_base.shared.domain.mapper

import com.spotify.app.core_base.shared.data.base.DtoMapper
import com.spotify.app.core_base.shared.data.dto.ArtistDTO
import com.spotify.app.core_base.shared.domain.model.Artist

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