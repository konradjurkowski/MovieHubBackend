package com.konradjurkowski.moviehub_server.core.model.dto.internal

data class ProductionCompanyDto(
    val id: Long,
    val name: String,
    val logoUrl: String?,
    val originCountry: String,
)
