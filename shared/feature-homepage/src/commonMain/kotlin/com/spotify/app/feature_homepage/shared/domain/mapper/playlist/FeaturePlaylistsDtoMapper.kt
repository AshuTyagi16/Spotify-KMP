package com.spotify.app.feature_homepage.shared.domain.mapper.playlist

import com.spotify.app.core_base.shared.data.base.DtoMapper
import com.spotify.app.feature_homepage.shared.data.dto.playlist.FeaturedPlaylistsDTO
import com.spotify.app.feature_homepage.shared.domain.model.playlist.FeaturedPlaylists

internal object FeaturePlaylistsDtoMapper : DtoMapper<FeaturedPlaylists, FeaturedPlaylistsDTO> {
    override fun asDto(domain: FeaturedPlaylists): FeaturedPlaylistsDTO {
        return FeaturedPlaylistsDTO(
            message = domain.message,
            playlists = PlaylistDTOMapper.asDto(domain.playlists)
        )
    }

    override fun asDomain(dto: FeaturedPlaylistsDTO): FeaturedPlaylists {
        return FeaturedPlaylists(
            message = dto.message,
            playlists = PlaylistDTOMapper.asDomain(dto.playlists)
        )
    }
}