package com.konradjurkowski.moviehub_server.core.model.dto.tmdb

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.konradjurkowski.moviehub_server.core.model.dto.internal.ProductionCountryDto

@JsonIgnoreProperties(ignoreUnknown = true)
data class TmdbProductionCountryDto(
    val name: String,
    val iso_3166_1: String,
)

fun TmdbProductionCountryDto.toDomain(): ProductionCountryDto {
    return ProductionCountryDto(
        name = name,
        iso_3166_1 = iso_3166_1,
    )
}
