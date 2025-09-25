package com.konradjurkowski.moviehub_server.core.model.dto.tmdb

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class TmdbSearchResponse<T>(
    val page: Long,
    val results: List<T>,
    @JsonProperty("total_pages")
    val totalPages: Long,
    @JsonProperty("total_results")
    val totalResults: Long,
)
