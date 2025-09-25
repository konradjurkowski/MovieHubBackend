package com.konradjurkowski.moviehub_server.feature.movie.model.dto.tmdb

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.konradjurkowski.moviehub_server.core.utils.constants.TmdbConstants
import com.konradjurkowski.moviehub_server.feature.movie.model.dto.MovieDto

@JsonIgnoreProperties(ignoreUnknown = true)
data class TmdbMovieDto(
    val id: Long,
    val adult: Boolean,
    @JsonProperty("backdrop_path")
    val backdropPath: String?,
    @JsonProperty("genre_ids")
    val genreIds: List<Int>,
    @JsonProperty("original_language")
    val originalLanguage: String,
    @JsonProperty("original_title")
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    @JsonProperty("poster_path")
    val posterPath: String?,
    @JsonProperty("release_date")
    val releaseDate: String?,
    val title: String,
    val video: Boolean,
    @JsonProperty("vote_average")
    val voteAverage: Double,
    @JsonProperty("vote_count")
    val voteCount: Int,
)

fun TmdbMovieDto.toDomain(): MovieDto {
    return MovieDto(
        tmdbId = id,
        title = title,
        overview = overview,
        language = originalLanguage,
        adult = adult,
        posterUrl = posterPath?.let { TmdbConstants.IMAGE_BASE_URL + it },
        backgroundUrl = backdropPath?.let { TmdbConstants.IMAGE_BASE_URL + it },
        releaseDate = releaseDate,
    )
}
