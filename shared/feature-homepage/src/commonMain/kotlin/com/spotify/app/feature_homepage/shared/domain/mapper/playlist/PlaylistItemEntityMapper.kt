package com.spotify.app.feature_homepage.shared.domain.mapper.playlist

import com.spotify.app.core_base.shared.domain.mapper.EntityMapper
import com.spotify.app.feature_homepage.shared.domain.model.playlist.PlaylistItem
import com.spotify.app.featurehomepage.shared.PlaylistItemEntitity

internal object PlaylistItemEntityMapper : EntityMapper<PlaylistItem, PlaylistItemEntitity> {
    override fun asEntity(domain: PlaylistItem): PlaylistItemEntitity {
        return PlaylistItemEntitity(
            id = domain.id,
            description = domain.description,
            name = domain.name,
            image = domain.image,
            trackCount = domain.trackCount
        )
    }

    override fun asDomain(entity: PlaylistItemEntitity): PlaylistItem {
        return PlaylistItem(
            id = entity.id,
            description = entity.description,
            name = entity.name,
            image = entity.image,
            trackCount = entity.trackCount
        )
    }
}