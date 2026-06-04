package com.konradjurkowski.moviehub_server.feature.movie.controller

import com.konradjurkowski.moviehub_server.core.model.dto.response.SearchResponse
import com.konradjurkowski.moviehub_server.feature.movie.model.dto.MovieDto
import com.konradjurkowski.moviehub_server.feature.movie.model.dto.create.CreateMovieRequest
import com.konradjurkowski.moviehub_server.feature.movie.model.dto.response.AddedMovieIdsResponse
import com.konradjurkowski.moviehub_server.feature.movie.service.MovieService
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
@RequestMapping("/api/movies")
class MovieController(
    private val movieService: MovieService,
) {

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    fun addMovie(@Valid @RequestBody request: CreateMovieRequest): MovieDto {
        return movieService.addMovie(request)
    }

    @GetMapping("/ids")
    fun getAddedTmdbIds(): AddedMovieIdsResponse {
        return movieService.getAddedTmdbIds()
    }

    @GetMapping("/leaderboard")
    fun getMovieLeaderboard(
        @RequestParam(defaultValue = "1") page: Int,
    ): SearchResponse<MovieDto> {
        return movieService.getMovieLeaderboard(page = page)
    }

    @GetMapping("/popular")
    fun getPopularMovies(
        @RequestParam(defaultValue = "1") page: Int,
        @RequestParam(required = false) language: String?,
    ): Mono<SearchResponse<MovieDto>> {
        return movieService.getPopularMovies(page = page, language = language)
    }

    @GetMapping("/search")
    fun searchMovies(
        @RequestParam query: String,
        @RequestParam(defaultValue = "1") page: Int,
        @RequestParam(required = false) language: String?,
    ): Mono<SearchResponse<MovieDto>> {
        return movieService.searchMovies(query = query, page = page, language = language)
    }

    @GetMapping("/preview/{tmdbId}")
    fun getMoviePreview(
        @PathVariable tmdbId: Long,
        @RequestParam(required = false) language: String?,
    ): Mono<MovieDto> {
        return movieService.getMoviePreview(tmdbId = tmdbId, language = language)
    }
}
