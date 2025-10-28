package com.konradjurkowski.moviehub_server.feature.movie.service

import com.konradjurkowski.moviehub_server.core.model.dto.response.ErrorCode
import com.konradjurkowski.moviehub_server.core.model.dto.response.SearchResponse
import com.konradjurkowski.moviehub_server.core.model.dto.tmdb.TmdbSearchResponse
import com.konradjurkowski.moviehub_server.core.utils.exceptions.ApiException
import com.konradjurkowski.moviehub_server.feature.group.repository.GroupRepository
import com.konradjurkowski.moviehub_server.feature.movie.model.dto.MovieDetailsDto
import com.konradjurkowski.moviehub_server.feature.movie.model.dto.MovieDto
import com.konradjurkowski.moviehub_server.feature.movie.model.dto.create.CreateMovieRequest
import com.konradjurkowski.moviehub_server.feature.movie.model.dto.response.AddedTmdbIdsResponse
import com.konradjurkowski.moviehub_server.feature.movie.model.dto.tmdb.TmdbMovieDetailsDto
import com.konradjurkowski.moviehub_server.feature.movie.model.dto.tmdb.TmdbMovieDto
import com.konradjurkowski.moviehub_server.feature.movie.model.dto.tmdb.toDomain
import com.konradjurkowski.moviehub_server.feature.movie.model.entity.Movie
import com.konradjurkowski.moviehub_server.feature.movie.model.entity.toDto
import com.konradjurkowski.moviehub_server.feature.movie.repository.MovieRepository
import org.springframework.core.ParameterizedTypeReference
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
class MovieService(
    private val tmdbClient: WebClient,
    private val movieRepository: MovieRepository,
    private val groupRepository: GroupRepository,
) {

    fun addMovie(request: CreateMovieRequest): MovieDto {
        val group = groupRepository.findById(request.groupId)
            .orElseThrow { ApiException(ErrorCode.GROUP_NOT_FOUND) }

        if (movieRepository.existsByGroupIdAndTmdbId(groupId = group.id, tmdbId = request.tmdbId)) {
            throw ApiException(ErrorCode.MOVIE_ALREADY_EXISTS)
        }

        val movie = Movie(
            group = group,
            tmdbId = request.tmdbId,
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

    fun getAddedTmdbIds(groupId: Long): AddedTmdbIdsResponse {
        return AddedTmdbIdsResponse(movies = movieRepository.findMovieTmdbIdsByGroupId(groupId))
    }

    fun getMovieLeaderboard(
        groupId: Long,
        page: Int = 1,
    ): SearchResponse<MovieDto> {
        val pageNumber = (page.coerceAtLeast(1) - 1)
        val pageable = PageRequest.of(
            pageNumber,
            20,
            Sort.by(Sort.Direction.DESC, "id"),
        )
        val moviesPage = movieRepository.findByGroupId(groupId, pageable)
        return SearchResponse(
            page = (moviesPage.number + 1).toLong(),
            results = moviesPage.content.map { it.toDto() },
            totalPages = moviesPage.totalPages.toLong(),
            totalResults = moviesPage.totalElements,
        )
    }

    fun searchMovies(
        query: String,
        page: Int = 1,
        language: String? = null,
    ): Mono<SearchResponse<MovieDto>> =
        tmdbClient.get()
            .uri { uri ->
                uri.path("/3/search/movie")
                    .queryParam("query", query)
                    .queryParam("page", page)
                    .queryParam("language", language ?: "en-US")
                    .queryParam("include_adult", false)
                    .build()
            }
            .retrieve()
            .bodyToMono(object : ParameterizedTypeReference<TmdbSearchResponse<TmdbMovieDto>>() {})
            .map { response ->
                SearchResponse(
                    page = response.page,
                    results = response.results.map { it.toDomain() },
                    totalPages = response.totalPages,
                    totalResults = response.totalResults,
                )
            }

    fun getPopularMovies(
        page: Int = 1,
        language: String? = null,
    ) : Mono<SearchResponse<MovieDto>> =
        tmdbClient.get()
            .uri { uri ->
                uri.path("/3/movie/popular")
                    .queryParam("page", page)
                    .queryParam("language", language ?: "en-US")
                    .queryParam("include_adult", false)
                    .build()
            }
            .retrieve()
            .bodyToMono(object : ParameterizedTypeReference<TmdbSearchResponse<TmdbMovieDto>>() {})
            .map { response ->
                SearchResponse(
                    page = response.page,
                    results = response.results.map { it.toDomain() },
                    totalPages = response.totalPages,
                    totalResults = response.totalResults,
                )
            }

    fun getMoviePreview(
        tmdbId: Long,
        language: String? = null,
    ) : Mono<MovieDetailsDto> =
        tmdbClient.get()
            .uri { uri ->
                uri.path("/3/movie/$tmdbId")
                    .queryParam("append_to_response", "videos,credits,watch/providers")
                    .queryParam("language", language ?: "en-US")
                    .build()
            }
            .retrieve()
            .bodyToMono(TmdbMovieDetailsDto::class.java)
            .map { it.toDomain() }
}
