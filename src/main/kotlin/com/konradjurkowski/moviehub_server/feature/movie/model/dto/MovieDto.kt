package com.konradjurkowski.moviehub_server.feature.movie.model.dto

import com.konradjurkowski.moviehub_server.core.model.dto.response.ApiResponse

data class MovieDto(
    val id: Long? = null,
    val groupId: Long? = null,
    val tmdbId: Long,
    val title: String,
    val overview: String,
    val language: String,
    val adult: Boolean,
    val posterUrl: String? = null,
    val backgroundUrl: String? = null,
    val releaseDate: String? = null,
) : ApiResponse
