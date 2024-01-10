package com.spotify.app.feature_playlist_detail.shared.domain.mapper

import com.spotify.app.core_base.shared.data.base.DtoMapper
import com.spotify.app.core_base.shared.domain.mapper.AlbumDtoMapper
import com.spotify.app.core_base.shared.domain.mapper.AlbumItemDtoMapper
import com.spotify.app.feature_playlist_detail.shared.data.dto.TrackDTO
import com.spotify.app.feature_playlist_detail.shared.domain.model.Track

object TrackDtoMapper : DtoMapper<Track, TrackDTO> {
    override fun asDto(domain: Track): TrackDTO {
        return TrackDTO(
            id = domain.id,
            name = domain.name,
            popularity = domain.popularity,
            previewUrl = domain.previewUrl,
            track = domain.track,
            durationMs = domain.durationMs,
            episode = domain.episode,
            explicit = domain.explicit,
            trackNumber = domain.trackNumber,
            album = AlbumItemDtoMapper.asDto(domain.album)
        )
    }

    override fun asDomain(dto: TrackDTO): Track {
        return Track(
            id = dto.id,
            name = dto.name,
            popularity = dto.popularity,
            previewUrl = dto.previewUrl,
            track = dto.track,
            durationMs = dto.durationMs,
            episode = dto.episode,
            explicit = dto.explicit,
            trackNumber = dto.trackNumber,
            album = AlbumItemDtoMapper.asDomain(dto.album)
        )
    }

}