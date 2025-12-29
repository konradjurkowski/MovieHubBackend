package com.konradjurkowski.moviehub_server.core.data.api.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.konradjurkowski.moviehub_server.core.model.dto.media.ProductionCompanyDto
import com.konradjurkowski.moviehub_server.core.utils.constants.TmdbConstants

@JsonIgnoreProperties(ignoreUnknown = true)
data class TmdbProductionCompany(
    val id: Long,
    @param:JsonProperty("logo_path")
    val logoPath: String? = null,
    val name: String,
    @param:JsonProperty("origin_country")
    val originCountry: String,
)

fun TmdbProductionCompany.toDto(): ProductionCompanyDto {
    return ProductionCompanyDto(
        id = id,
        logoUrl = logoPath?.let{ TmdbConstants.IMAGE_BASE_URL + it },
        name = name,
        originCountry = originCountry,
    )
}
