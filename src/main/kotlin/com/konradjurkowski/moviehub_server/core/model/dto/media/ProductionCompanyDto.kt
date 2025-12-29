package com.konradjurkowski.moviehub_server.core.model.dto.media

data class ProductionCompanyDto(
    val id: Long,
    val name: String,
    val logoUrl: String? = null,
    val originCountry: String,
)
