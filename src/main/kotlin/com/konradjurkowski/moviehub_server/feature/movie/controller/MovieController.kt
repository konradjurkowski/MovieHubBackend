package com.konradjurkowski.moviehub_server.feature.movie.controller

import com.konradjurkowski.moviehub_server.core.model.dto.response.ApiResponse
import com.konradjurkowski.moviehub_server.core.utils.ApiHandler
import com.konradjurkowski.moviehub_server.feature.movie.model.dto.create.CreateMovieRequest
import com.konradjurkowski.moviehub_server.feature.movie.service.MovieService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/movies")
class MovieController(
    private val movieService: MovieService,
) {

    @PostMapping("/add")
    fun addMovie(@RequestBody request: CreateMovieRequest): ResponseEntity<ApiResponse> {
        return ApiHandler.execute(status = HttpStatus.CREATED) { movieService.addMovie(request) }
    }

    @GetMapping("/ids")
    fun getAddedTmdbIds(): ResponseEntity<ApiResponse> {
        return ApiHandler.execute { movieService.getAddedTmdbIds() }
    }

    @GetMapping("/leaderboard")
    fun getMovieLeaderboard(
        @RequestParam(defaultValue = "1") page: Int,
    ): ResponseEntity<ApiResponse> {
        return ApiHandler.execute { movieService.getMovieLeaderboard(page = page) }
    }

    @GetMapping("/popular")
    fun getPopularMovies(
        @RequestParam(defaultValue = "1") page: Int,
        @RequestParam(required = false) language: String?,
    ): Mono<ResponseEntity<ApiResponse>> {
        return ApiHandler.executeReactive { movieService.getPopularMovies(page = page, language = language) }
    }

    @GetMapping("/search")
    fun searchMovies(
        @RequestParam query: String,
        @RequestParam(defaultValue = "1") page: Int,
        @RequestParam(required = false) language: String?,
    ): Mono<ResponseEntity<ApiResponse>> {
        return ApiHandler.executeReactive { movieService.searchMovies(query = query, page = page, language = language) }
    }

    @GetMapping("/preview/{tmdbId}")
    fun getMoviePreview(
        @PathVariable tmdbId: Long,
        @RequestParam(required = false) language: String?
    ): Mono<ResponseEntity<ApiResponse>> {
        return ApiHandler.executeReactive { movieService.getMoviePreview(tmdbId = tmdbId, language = language) }
    }
}
