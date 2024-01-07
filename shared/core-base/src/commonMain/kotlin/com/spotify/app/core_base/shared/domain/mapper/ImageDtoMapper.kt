package com.spotify.app.core_base.shared.domain.mapper

import com.spotify.app.core_base.shared.data.base.DtoMapper
import com.spotify.app.core_base.shared.data.dto.ImageDTO
import com.spotify.app.core_base.shared.domain.model.Image

object ImageDtoMapper : DtoMapper<Image, ImageDTO> {
    override fun asDto(domain: Image): ImageDTO {
        return ImageDTO(
            url = domain.url
        )
    }

    override fun asDomain(dto: ImageDTO): Image {
        return Image(
            url = dto.url
        )
    }
}