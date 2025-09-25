package com.konradjurkowski.moviehub_server.core.model.dto.tmdb

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class TmdbGenreDto(
    val id: Long,
    val name: String,
)
