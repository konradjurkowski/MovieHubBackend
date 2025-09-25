package com.konradjurkowski.moviehub_server.feature.movie.model.dto.tmdb

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.konradjurkowski.moviehub_server.core.model.dto.tmdb.TmdbCastDto
import com.konradjurkowski.moviehub_server.core.model.dto.tmdb.TmdbCrewDto
import com.konradjurkowski.moviehub_server.core.model.dto.tmdb.TmdbGenreDto
import com.konradjurkowski.moviehub_server.core.model.dto.tmdb.TmdbProductionCompanyDto
import com.konradjurkowski.moviehub_server.core.model.dto.tmdb.TmdbProductionCountryDto
import com.konradjurkowski.moviehub_server.core.model.dto.tmdb.TmdbSpokenLanguageDto
import com.konradjurkowski.moviehub_server.core.model.dto.tmdb.TmdbVideoDto
import com.konradjurkowski.moviehub_server.core.model.dto.tmdb.WatchProviderDataDto
import com.konradjurkowski.moviehub_server.core.model.dto.tmdb.toDomain
import com.konradjurkowski.moviehub_server.core.utils.constants.TmdbConstants
import com.konradjurkowski.moviehub_server.feature.movie.model.dto.MovieDetailsDto

@JsonIgnoreProperties(ignoreUnknown = true)
data class TmdbMovieDetailsDto(
    val id: Long,
    val adult: Boolean,
    @JsonProperty("backdrop_path")
    val backdropPath: String?,
    val budget: Long,
    val genres: List<TmdbGenreDto>,
    val homepage: String?,
    @JsonProperty("imdb_id")
    val imdbId: String?,
    @JsonProperty("origin_country")
    val originCountry: List<String>,
    @JsonProperty("original_language")
    val originalLanguage: String,
    @JsonProperty("original_title")
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    @JsonProperty("poster_path")
    val posterPath: String?,
    @JsonProperty("production_companies")
    val productionCompanies: List<TmdbProductionCompanyDto>,
    @JsonProperty("production_countries")
    val productionCountries: List<TmdbProductionCountryDto>,
    @JsonProperty("release_date")
    val releaseDate: String?,
    val revenue: Long,
    val runtime: Long,
    @JsonProperty("spoken_languages")
    val spokenLanguages: List<TmdbSpokenLanguageDto>,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    @JsonProperty("vote_average")
    val voteAverage: Double,
    @JsonProperty("vote_count")
    val voteCount: Int,
    val videos: VideosResponse?,
    val credits: CreditsResponse?,
    @JsonProperty("watch/providers")
    val watchProviders: WatchProvidersResponse?,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class VideosResponse(
    val results: List<TmdbVideoDto>,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class CreditsResponse(
    val cast: List<TmdbCastDto>,
    val crew: List<TmdbCrewDto>,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class WatchProvidersResponse(
    val results: WatchProviderDataDto,
)

fun TmdbMovieDetailsDto.toDomain(): MovieDetailsDto {
    return MovieDetailsDto(
        tmdbId = id,
        title = title,
        overview = overview,
        language = originalLanguage,
        adult = adult,
        genres = genres,
        homepage = homepage,
        originCountry = originCountry,
        popularity = popularity,
        posterUrl = posterPath?.let { TmdbConstants.IMAGE_BASE_URL + it },
        backgroundUrl = backdropPath?.let { TmdbConstants.IMAGE_BASE_URL + it },
        releaseDate = releaseDate,
        productionCompanies = productionCompanies.map { it.toDomain() },
        productionCountries = productionCountries.map { it.toDomain() },
        revenue = revenue,
        runtime = runtime,
        spokenLanguages = spokenLanguages.map { it.toDomain() },
        status = status,
        tagline = tagline,
        videos = videos?.results?.map { it.toDomain() } ?: emptyList(),
        cast = credits?.cast?.map { it.toDomain() } ?: emptyList(),
        crew = credits?.crew?.map { it.toDomain() } ?: emptyList(),
        watchProviders = watchProviders?.results,
    )
}
