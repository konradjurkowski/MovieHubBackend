package com.konradjurkowski.moviehub_server.core.model.dto.tmdb

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.konradjurkowski.moviehub_server.core.model.dto.internal.ProductionCompanyDto
import com.konradjurkowski.moviehub_server.core.utils.constants.TmdbConstants

@JsonIgnoreProperties(ignoreUnknown = true)
data class TmdbProductionCompanyDto(
    val id: Long,
    @JsonProperty("logo_path")
    val logoPath: String?,
    val name: String,
    @JsonProperty("origin_country")
    val originCountry: String,
)

fun TmdbProductionCompanyDto.toDomain(): ProductionCompanyDto {
    return ProductionCompanyDto(
        id = id,
        logoUrl = logoPath?.let{ TmdbConstants.IMAGE_BASE_URL + it },
        name = name,
        originCountry = originCountry,
    )
}
