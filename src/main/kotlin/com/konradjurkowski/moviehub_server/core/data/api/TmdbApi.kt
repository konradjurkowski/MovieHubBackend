package com.konradjurkowski.moviehub_server.core.data.api

import com.konradjurkowski.moviehub_server.core.data.properties.TmdbProperties
import com.konradjurkowski.moviehub_server.core.data.api.dto.TmdbSearchResponse
import com.konradjurkowski.moviehub_server.core.data.api.dto.TmdbMovie
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
class TmdbApi(properties: TmdbProperties) {

    private val webClient = WebClient.builder()
        .baseUrl(properties.baseUrl)
        .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer ${properties.token}")
        .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
        .exchangeStrategies(
            ExchangeStrategies.builder()
                .codecs { it.defaultCodecs().maxInMemorySize(4 * 1024 * 1024) }
                .build()
        )
        .build()

    fun getMovieById(
        id: Long,
        language: String? = null,
    ): Mono<TmdbMovie> =
        webClient.get()
            .uri { uri ->
                uri.path("/3/movie/$id")
                    .queryParam("append_to_response", "videos,credits,watch/providers")
                    .queryParam("language", language ?: "en-US")
                    .build()
            }
            .retrieve()
            .bodyToMono(TmdbMovie::class.java)

    fun getPopularMovies(
        page: Int = 1,
        language: String? = null,
    ): Mono<TmdbSearchResponse<TmdbMovie>> =
        webClient.get()
            .uri { uri ->
                uri.path("/3/movie/popular")
                    .queryParam("page", page)
                    .queryParam("language", language ?: "en-US")
                    .queryParam("include_adult", false)
                    .build()
            }
            .retrieve()
            .bodyToMono(object : ParameterizedTypeReference<TmdbSearchResponse<TmdbMovie>>() {})

    fun searchMovies(
        query: String,
        page: Int = 1,
        language: String? = null,
    ): Mono<TmdbSearchResponse<TmdbMovie>> =
        webClient.get()
            .uri { uri ->
                uri.path("/3/search/movie")
                    .queryParam("query", query)
                    .queryParam("page", page)
                    .queryParam("language", language ?: "en-US")
                    .queryParam("include_adult", false)
                    .build()
            }
            .retrieve()
            .bodyToMono(object : ParameterizedTypeReference<TmdbSearchResponse<TmdbMovie>>() {})
}
