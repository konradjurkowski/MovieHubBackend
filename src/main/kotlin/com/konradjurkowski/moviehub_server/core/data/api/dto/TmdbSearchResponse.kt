package com.konradjurkowski.moviehub_server.core.data.api.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.konradjurkowski.moviehub_server.core.model.dto.response.SearchResponse

@JsonIgnoreProperties(ignoreUnknown = true)
data class TmdbSearchResponse<T>(
    val page: Long,
    val results: List<T>,
    @param:JsonProperty("total_pages")
    val totalPages: Long,
    @param:JsonProperty("total_results")
    val totalResults: Long,
)

fun <T, R> TmdbSearchResponse<T>.toDto(mapper: (T) -> R): SearchResponse<R> {
    return SearchResponse(
        page = page,
        results = results.map(mapper),
        totalPages = totalPages,
        totalResults = totalResults
    )
}