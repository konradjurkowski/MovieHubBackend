package com.konradjurkowski.moviehub_server.core.data.api.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class TmdbCredits(
    val cast: List<TmdbCast>,
    val crew: List<TmdbCrew>,
)
