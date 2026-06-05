package com.konradjurkowski.moviehub_server.core.model.dto.media

data class CreatorDto(
    val id: Long,
    val name: String,
    val originalName: String? = null,
    val gender: Long? = null,
    val imageUrl: String? = null,
)
