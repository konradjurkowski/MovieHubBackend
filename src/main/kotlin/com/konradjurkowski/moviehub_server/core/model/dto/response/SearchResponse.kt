package com.konradjurkowski.moviehub_server.core.model.dto.response

data class SearchResponse<T>(
    val page: Long,
    val results: List<T>,
    val totalPages: Long,
    val totalResults: Long,
) : ApiResponse
