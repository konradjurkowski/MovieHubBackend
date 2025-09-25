package com.konradjurkowski.moviehub_server.feature.movie.model.dto.response

import com.konradjurkowski.moviehub_server.core.model.dto.response.ApiResponse

data class AddedTmdbIdsResponse(val movies: List<Long>) : ApiResponse
