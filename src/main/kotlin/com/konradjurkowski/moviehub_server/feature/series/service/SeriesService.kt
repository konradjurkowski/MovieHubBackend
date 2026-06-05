package com.konradjurkowski.moviehub_server.feature.series.service

import com.konradjurkowski.moviehub_server.core.data.api.TmdbApi
import com.konradjurkowski.moviehub_server.core.data.api.dto.TmdbSeries
import com.konradjurkowski.moviehub_server.core.data.api.dto.toDto
import com.konradjurkowski.moviehub_server.core.model.dto.response.ErrorCode
import com.konradjurkowski.moviehub_server.core.model.dto.response.SearchResponse
import com.konradjurkowski.moviehub_server.core.utils.exceptions.ApiException
import com.konradjurkowski.moviehub_server.feature.series.model.dto.SeriesDto
import com.konradjurkowski.moviehub_server.feature.series.model.dto.create.CreateSeriesRequest
import com.konradjurkowski.moviehub_server.feature.series.model.dto.response.AddedSeriesIdsResponse
import com.konradjurkowski.moviehub_server.feature.series.model.entity.Series
import com.konradjurkowski.moviehub_server.feature.series.model.entity.toDto
import com.konradjurkowski.moviehub_server.feature.series.repository.SeriesRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class SeriesService(
    private val tmdbApi: TmdbApi,
    private val seriesRepository: SeriesRepository,
) {

    fun addSeries(request: CreateSeriesRequest): SeriesDto {
        if (seriesRepository.existsById(request.tmdbId)) {
            throw ApiException(ErrorCode.SERIES_ALREADY_EXISTS)
        }

        val series = Series(
            id = request.tmdbId,
            title = request.title,
            overview = request.overview,
            language = request.language,
            adult = request.adult,
            posterUrl = request.posterUrl,
            backgroundUrl = request.backgroundUrl,
            releaseDate = request.releaseDate,
        )
        return seriesRepository.save(series).toDto()
    }

    fun getAddedSeriesIds(): AddedSeriesIdsResponse {
        val seriesIds = seriesRepository.findAllIds()
        return AddedSeriesIdsResponse(seriesIds)
    }

    fun getSeriesLeaderboard(page: Int = 1): SearchResponse<SeriesDto> {
        val pageNumber = (page.coerceAtLeast(1) - 1)
        val pageable = PageRequest.of(
            pageNumber,
            20,
            Sort.by(Sort.Direction.DESC, "createdAt"),
        )
        val seriesPage = seriesRepository.findAll(pageable)
        return SearchResponse(
            page = (seriesPage.number + 1).toLong(),
            results = seriesPage.content.map { it.toDto() },
            totalPages = seriesPage.totalPages.toLong(),
            totalResults = seriesPage.totalElements,
        )
    }

    fun getPopularSeries(
        page: Int = 1,
        language: String? = null,
    ): Mono<SearchResponse<SeriesDto>> {
        return tmdbApi.getPopularSeries(page = page, language = language)
            .map { it.toDto(TmdbSeries::toDto) }
    }

    fun searchSeries(
        query: String,
        page: Int = 1,
        language: String? = null,
    ): Mono<SearchResponse<SeriesDto>> {
        return tmdbApi.searchSeries(query = query, page = page, language = language)
            .map { it.toDto(TmdbSeries::toDto) }
    }

    fun getSeriesPreview(
        id: Long,
        language: String? = null,
    ): Mono<SeriesDto> {
        return tmdbApi.getSeriesById(id = id, language = language)
            .map { it.toDto() }
    }
}
