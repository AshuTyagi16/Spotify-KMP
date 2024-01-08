package com.spotify.app.core_base.shared.domain.mapper

interface EntityMapper<Domain, Entity> {

    fun asEntity(domain: Domain): Entity

    fun asDomain(entity: Entity): Domain

}