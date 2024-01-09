package com.spotify.app.feature_homepage.shared.domain.mapper.album

import com.spotify.app.core_base.shared.domain.mapper.EntityMapper
import com.spotify.app.feature_homepage.shared.domain.model.album.AlbumItem
import com.spotify.app.featurehomepage.shared.AlbumItemEntity

object AlbumItemEntityMapper : EntityMapper<AlbumItem, AlbumItemEntity> {
    override fun asEntity(domain: AlbumItem): AlbumItemEntity {
        return AlbumItemEntity(
            id = domain.id,
            name = domain.name,
            image = domain.image,
            trackCount = domain.trackCount,
            releaseDate = domain.releaseDate,
            artists = domain.artists
        )
    }

    override fun asDomain(entity: AlbumItemEntity): AlbumItem {
        return AlbumItem(
            id = entity.id,
            name = entity.name,
            image = entity.image,
            releaseDate = entity.releaseDate,
            trackCount = entity.trackCount,
            artists = entity.artists
        )
    }

}