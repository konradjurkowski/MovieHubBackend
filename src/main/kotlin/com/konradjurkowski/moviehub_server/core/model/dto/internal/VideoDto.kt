package com.konradjurkowski.moviehub_server.core.model.dto.internal

data class VideoDto(
    val id: String,
    val name: String,
    val key: String,
    val site: String,
    val size: Long,
    val type: String,
    val official: Boolean,
    val iso_639_1: String,
    val iso_3166_1: String,
    val publishedAt: String,
)
