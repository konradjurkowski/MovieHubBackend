package com.konradjurkowski.moviehub_server.core.data.api.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.konradjurkowski.moviehub_server.core.model.dto.media.ProductionCountryDto

@JsonIgnoreProperties(ignoreUnknown = true)
data class TmdbProductionCountry(
    val name: String,
    val iso_3166_1: String,
)

fun TmdbProductionCountry.toDto(): ProductionCountryDto {
    return ProductionCountryDto(
        name = name,
        iso_3166_1 = iso_3166_1,
    )
}
