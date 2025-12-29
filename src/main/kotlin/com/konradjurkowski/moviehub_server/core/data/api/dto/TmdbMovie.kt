package com.konradjurkowski.moviehub_server.core.data.api.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.konradjurkowski.moviehub_server.core.utils.constants.TmdbConstants
import com.konradjurkowski.moviehub_server.feature.movie.model.dto.MovieDto

@JsonIgnoreProperties(ignoreUnknown = true)
data class TmdbMovie(
    val id: Long,
    val adult: Boolean,
    @param:JsonProperty("backdrop_path")
    val backdropPath: String? = null,
    val budget: Long? = null,
    val genres: List<TmdbGenre>? = null,
    val homepage: String? = null,
    @param:JsonProperty("imdb_id")
    val imdbId: String? = null,
    @param:JsonProperty("origin_country")
    val originCountry: List<String>? = null,
    @param:JsonProperty("original_language")
    val originalLanguage: String,
    @param:JsonProperty("original_title")
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    @param:JsonProperty("poster_path")
    val posterPath: String? = null,
    @param:JsonProperty("production_companies")
    val productionCompanies: List<TmdbProductionCompany>? = null,
    @param:JsonProperty("production_countries")
    val productionCountries: List<TmdbProductionCountry>? = null,
    @param:JsonProperty("release_date")
    val releaseDate: String? = null,
    val revenue: Long? = null,
    val runtime: Long? = null,
    @param:JsonProperty("spoken_languages")
    val spokenLanguages: List<TmdbSpokenLanguage>? = null,
    val status: String? = null,
    val tagline: String? = null,
    val title: String,
    val video: Boolean,
    @param:JsonProperty("vote_average")
    val voteAverage: Double,
    @param:JsonProperty("vote_count")
    val voteCount: Int,
    val videos: TmdbVideosResponse? = null,
    val credits: TmdbCredits? = null,
    @param:JsonProperty("watch/providers")
    val watchProviders: TmdbWatchProviders? = null,
)

fun TmdbMovie.toDto(): MovieDto {
    return MovieDto(
        tmdbId = id,
        title = title,
        overview = overview,
        language = originalLanguage,
        adult = adult,
        posterUrl = posterPath?.let { TmdbConstants.IMAGE_BASE_URL + it },
        backgroundUrl = backdropPath?.let { TmdbConstants.IMAGE_BASE_URL + it },
        genres = genres?.map { it.toDto() },
        homepage = homepage,
        popularity = popularity,
        productionCompanies = productionCompanies?.map { it.toDto() },
        productionCountries = productionCountries?.map { it.toDto() },
        spokenLanguages = spokenLanguages?.map { it.toDto() },
        status = status,
        tagline = tagline,
        revenue = revenue,
        runtime = runtime,
        cast = credits?.cast?.map { it.toDto() },
        crew = credits?.crew?.map { it.toDto() },
        videos = videos?.results?.map { it.toDto() },
        releaseDate = releaseDate,
    )
}
