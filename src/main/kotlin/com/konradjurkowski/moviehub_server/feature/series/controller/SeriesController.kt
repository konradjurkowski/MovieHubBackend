package com.konradjurkowski.moviehub_server.feature.series.controller

import com.konradjurkowski.moviehub_server.core.model.dto.response.SearchResponse
import com.konradjurkowski.moviehub_server.feature.series.model.dto.SeriesDto
import com.konradjurkowski.moviehub_server.feature.series.model.dto.create.CreateSeriesRequest
import com.konradjurkowski.moviehub_server.feature.series.model.dto.response.AddedSeriesIdsResponse
import com.konradjurkowski.moviehub_server.feature.series.service.SeriesService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/series")
class SeriesController(
    private val seriesService: SeriesService,
) {

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    fun addSeries(@Valid @RequestBody request: CreateSeriesRequest): SeriesDto {
        return seriesService.addSeries(request)
    }

    @GetMapping("/ids")
    fun getAddedSeriesIds(): AddedSeriesIdsResponse {
        return seriesService.getAddedSeriesIds()
    }

    @GetMapping("/leaderboard")
    fun getSeriesLeaderboard(
        @RequestParam(defaultValue = "1") page: Int,
    ): SearchResponse<SeriesDto> {
        return seriesService.getSeriesLeaderboard(page = page)
    }

    @GetMapping("/popular")
    fun getPopularSeries(
        @RequestParam(defaultValue = "1") page: Int,
        @RequestParam(required = false) language: String?,
    ): Mono<SearchResponse<SeriesDto>> {
        return seriesService.getPopularSeries(page = page, language = language)
    }

    @GetMapping("/search")
    fun searchSeries(
        @RequestParam query: String,
        @RequestParam(defaultValue = "1") page: Int,
        @RequestParam(required = false) language: String?,
    ): Mono<SearchResponse<SeriesDto>> {
        return seriesService.searchSeries(query = query, page = page, language = language)
    }

    @GetMapping("/preview/{id}")
    fun getSeriesPreview(
        @PathVariable id: Long,
        @RequestParam(required = false) language: String?,
    ): Mono<SeriesDto> {
        return seriesService.getSeriesPreview(id = id, language = language)
    }
}
