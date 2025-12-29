package com.konradjurkowski.moviehub_server.core.model.dto.media

data class CastDto(
    val id: Long,
    val name: String,
    val originalName: String,
    val character: String,
    val popularity: Double,
    val imageUrl: String? = null,
    val adult: Boolean,
    val gender: Long,
    val order: Long,
)
