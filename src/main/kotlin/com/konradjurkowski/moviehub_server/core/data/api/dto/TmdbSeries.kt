package com.konradjurkowski.moviehub_server.core.data.api.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.konradjurkowski.moviehub_server.core.utils.constants.TmdbConstants
import com.konradjurkowski.moviehub_server.feature.series.model.dto.SeriesDto

@JsonIgnoreProperties(ignoreUnknown = true)
data class TmdbSeries(
    val id: Long,
    val adult: Boolean,
    @param:JsonProperty("backdrop_path")
    val backdropPath: String? = null,
    @param:JsonProperty("created_by")
    val createdBy: List<TmdbCreator>? = null,
    @param:JsonProperty("episode_run_time")
    val episodeRunTime: List<Long>? = null,
    @param:JsonProperty("first_air_date")
    val firstAirDate: String? = null,
    val genres: List<TmdbGenre>? = null,
    val homepage: String? = null,
    @param:JsonProperty("in_production")
    val inProduction: Boolean? = null,
    @param:JsonProperty("last_air_date")
    val lastAirDate: String? = null,
    val name: String,
    val networks: List<TmdbProductionCompany>? = null,
    @param:JsonProperty("number_of_episodes")
    val numberOfEpisodes: Long? = null,
    @param:JsonProperty("number_of_seasons")
    val numberOfSeasons: Long? = null,
    @param:JsonProperty("origin_country")
    val originCountry: List<String>? = null,
    @param:JsonProperty("original_language")
    val originalLanguage: String,
    @param:JsonProperty("original_name")
    val originalName: String,
    val overview: String,
    val popularity: Double,
    @param:JsonProperty("poster_path")
    val posterPath: String? = null,
    @param:JsonProperty("production_companies")
    val productionCompanies: List<TmdbProductionCompany>? = null,
    @param:JsonProperty("production_countries")
    val productionCountries: List<TmdbProductionCountry>? = null,
    @param:JsonProperty("spoken_languages")
    val spokenLanguages: List<TmdbSpokenLanguage>? = null,
    val status: String? = null,
    val tagline: String? = null,
    val type: String? = null,
    @param:JsonProperty("vote_average")
    val voteAverage: Double,
    @param:JsonProperty("vote_count")
    val voteCount: Int,
    val videos: TmdbVideosResponse? = null,
    val credits: TmdbCredits? = null,
    @param:JsonProperty("watch/providers")
    val watchProviders: TmdbWatchProviders? = null,
)

fun TmdbSeries.toDto(): SeriesDto {
    return SeriesDto(
        id = id,
        title = name,
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
        type = type,
        numberOfSeasons = numberOfSeasons,
        numberOfEpisodes = numberOfEpisodes,
        episodeRunTime = episodeRunTime,
        inProduction = inProduction,
        createdBy = createdBy?.map { it.toDto() },
        networks = networks?.map { it.toDto() },
        cast = credits?.cast?.map { it.toDto() },
        crew = credits?.crew?.map { it.toDto() },
        videos = videos?.results?.map { it.toDto() },
        releaseDate = firstAirDate,
        lastAirDate = lastAirDate,
    )
}
