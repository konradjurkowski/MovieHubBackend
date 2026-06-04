package com.konradjurkowski.moviehub_server.feature.movie.model.dto

import com.konradjurkowski.moviehub_server.core.model.dto.media.CastDto
import com.konradjurkowski.moviehub_server.core.model.dto.media.CrewDto
import com.konradjurkowski.moviehub_server.core.model.dto.media.ProductionCompanyDto
import com.konradjurkowski.moviehub_server.core.model.dto.media.ProductionCountryDto
import com.konradjurkowski.moviehub_server.core.model.dto.media.SpokenLanguageDto
import com.konradjurkowski.moviehub_server.core.model.dto.media.VideoDto
import com.konradjurkowski.moviehub_server.core.model.dto.media.GenreDto

data class MovieDto(
    val id: Long? = null,
    val tmdbId: Long,
    val title: String,
    val overview: String,
    val language: String,
    val adult: Boolean,
    val posterUrl: String? = null,
    val backgroundUrl: String? = null,
    val genres: List<GenreDto>? = null,
    val homepage: String? = null,
    val popularity: Double? = null,
    val productionCompanies: List<ProductionCompanyDto>? = null,
    val productionCountries: List<ProductionCountryDto>? = null,
    val spokenLanguages: List<SpokenLanguageDto>? = null,
    val status: String? = null,
    val tagline: String? = null,
    val revenue: Long? = null,
    val runtime: Long? = null,
    val cast: List<CastDto>? = null,
    val crew: List<CrewDto>? = null,
    val videos: List<VideoDto>? = null,
    val releaseDate: String? = null,
)
