package com.konradjurkowski.moviehub_server.feature.movie.model.dto

import com.konradjurkowski.moviehub_server.core.model.dto.response.ApiResponse
import com.konradjurkowski.moviehub_server.core.model.dto.internal.ProductionCompanyDto
import com.konradjurkowski.moviehub_server.core.model.dto.internal.CastDto
import com.konradjurkowski.moviehub_server.core.model.dto.internal.CrewDto
import com.konradjurkowski.moviehub_server.core.model.dto.internal.ProductionCountryDto
import com.konradjurkowski.moviehub_server.core.model.dto.internal.SpokenLanguageDto
import com.konradjurkowski.moviehub_server.core.model.dto.internal.VideoDto
import com.konradjurkowski.moviehub_server.core.model.dto.tmdb.TmdbGenreDto
import com.konradjurkowski.moviehub_server.core.model.dto.tmdb.WatchProviderDataDto

data class MovieDetailsDto(
    val id: Long? = null,
    val groupId: Long? = null,
    val tmdbId: Long,
    val title: String,
    val overview: String,
    val language: String,
    val adult: Boolean,
    val genres: List<TmdbGenreDto>,
    val homepage: String?,
    val originCountry: List<String>,
    val popularity: Double,
    val posterUrl: String?,
    val backgroundUrl: String? = null,
    val releaseDate: String? = null,
    val productionCompanies: List<ProductionCompanyDto>,
    val productionCountries: List<ProductionCountryDto>,
    val revenue: Long,
    val runtime: Long,
    val spokenLanguages: List<SpokenLanguageDto>,
    val status: String,
    val tagline: String,
    val videos: List<VideoDto>,
    val cast: List<CastDto>,
    val crew: List<CrewDto>,
    val watchProviders: WatchProviderDataDto? = null,
) : ApiResponse
