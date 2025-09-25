package com.konradjurkowski.moviehub_server.feature.movie.model.dto.create

data class CreateMovieRequest(
    val groupId: Long,
    val tmdbId: Long,
    val title: String,
    val overview: String,
    val language: String,
    val adult: Boolean,
    val posterUrl: String? = null,
    val backgroundUrl: String? = null,
    val releaseDate: String? = null,
)
