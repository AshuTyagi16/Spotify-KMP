package com.spotify.app.feature_homepage.shared.domain.mapper

import com.spotify.app.core_base.shared.data.base.DtoMapper
import com.spotify.app.feature_homepage.shared.data.dto.TracksInfoDto
import com.spotify.app.feature_homepage.shared.domain.model.TracksInfo

internal object TrackInfoDtoMapper : DtoMapper<TracksInfo, TracksInfoDto> {
    override fun asDto(domain: TracksInfo): TracksInfoDto {
        return TracksInfoDto(total = domain.total)
    }

    override fun asDomain(dto: TracksInfoDto): TracksInfo {
        return TracksInfo(total = dto.total)
    }
}