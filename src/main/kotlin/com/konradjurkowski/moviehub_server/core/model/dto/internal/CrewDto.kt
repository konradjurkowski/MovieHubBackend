package com.konradjurkowski.moviehub_server.core.model.dto.internal

data class CrewDto(
    val id: Long,
    val name: String,
    val originalName: String,
    val popularity: Double,
    val imageUrl: String?,
    val adult: Boolean,
    val gender: Long,
    val department: String,
    val job: String,
)
