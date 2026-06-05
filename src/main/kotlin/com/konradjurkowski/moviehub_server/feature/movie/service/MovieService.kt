package com.konradjurkowski.moviehub_server.feature.movie.service

import com.konradjurkowski.moviehub_server.core.data.api.TmdbApi
import com.konradjurkowski.moviehub_server.core.model.dto.response.ErrorCode
import com.konradjurkowski.moviehub_server.core.model.dto.response.SearchResponse
import com.konradjurkowski.moviehub_server.core.utils.exceptions.ApiException
import com.konradjurkowski.moviehub_server.feature.movie.model.dto.MovieDto
import com.konradjurkowski.moviehub_server.feature.movie.model.dto.create.CreateMovieRequest
import com.konradjurkowski.moviehub_server.feature.movie.model.dto.response.AddedMovieIdsResponse
import com.konradjurkowski.moviehub_server.core.data.api.dto.TmdbMovie
import com.konradjurkowski.moviehub_server.core.data.api.dto.toDto
import com.konradjurkowski.moviehub_server.feature.movie.model.entity.Movie
import com.konradjurkowski.moviehub_server.feature.movie.model.entity.toDto
import com.konradjurkowski.moviehub_server.feature.movie.repository.MovieRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class MovieService(
    private val tmdbApi: TmdbApi,
    private val movieRepository: MovieRepository,
) {

    fun addMovie(request: CreateMovieRequest): MovieDto {
        if (movieRepository.existsById(request.tmdbId)) {
            throw ApiException(ErrorCode.MOVIE_ALREADY_EXISTS)
        }

        val movie = Movie(
            id = request.tmdbId,
            title = request.title,
            overview = request.overview,
            language = request.language,
            adult = request.adult,
            posterUrl = request.posterUrl,
            backgroundUrl = request.backgroundUrl,
            releaseDate = request.releaseDate,
        )
        return movieRepository.save(movie).toDto()
    }

    fun getAddedMovieIds(): AddedMovieIdsResponse {
        val movieIds = movieRepository.findAllIds()
        return AddedMovieIdsResponse(movieIds)
    }

    fun getMovieLeaderboard(page: Int = 1): SearchResponse<MovieDto> {
        val pageNumber = (page.coerceAtLeast(1) - 1)
        val pageable = PageRequest.of(
            pageNumber,
            20,
            Sort.by(Sort.Direction.DESC, "createdAt"),
        )
        val moviesPage = movieRepository.findAll(pageable)
        return SearchResponse(
            page = (moviesPage.number + 1).toLong(),
            results = moviesPage.content.map { it.toDto() },
            totalPages = moviesPage.totalPages.toLong(),
            totalResults = moviesPage.totalElements,
        )
    }

    fun getPopularMovies(
        page: Int = 1,
        language: String? = null,
    ): Mono<SearchResponse<MovieDto>> {
        return tmdbApi.getPopularMovies(page = page, language = language)
            .map { it.toDto(TmdbMovie::toDto) }
    }

    fun searchMovies(
        query: String,
        page: Int = 1,
        language: String? = null,
    ): Mono<SearchResponse<MovieDto>> {
        return tmdbApi.searchMovies(query = query, page = page, language = language)
            .map { it.toDto(TmdbMovie::toDto) }
    }

    fun getMoviePreview(
        id: Long,
        language: String? = null,
    ): Mono<MovieDto> {
        return tmdbApi.getMovieById(id = id, language = language)
            .map { it.toDto() }
    }
}
