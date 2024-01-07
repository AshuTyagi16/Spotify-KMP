package com.spotify.app.core_base.shared.data.base

interface DtoMapper<Domain, DTO> {

    fun asDto(domain: Domain): DTO

    fun asDomain(dto: DTO): Domain
}