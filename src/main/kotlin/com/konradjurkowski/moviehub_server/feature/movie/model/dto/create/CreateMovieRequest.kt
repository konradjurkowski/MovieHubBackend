package com.konradjurkowski.moviehub_server.feature.movie.model.dto.create

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive

data class CreateMovieRequest(
    @field:Positive
    val tmdbId: Long,
    @field:NotBlank
    val title: String,
    val overview: String,
    val language: String,
    val adult: Boolean,
    val posterUrl: String? = null,
    val backgroundUrl: String? = null,
    val releaseDate: String? = null,
)
