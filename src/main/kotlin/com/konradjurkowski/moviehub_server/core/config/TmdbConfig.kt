package com.konradjurkowski.moviehub_server.core.config

import com.konradjurkowski.moviehub_server.core.data.properties.TmdbProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class TmdbConfig(
    private val properties: TmdbProperties,
) {

    @Bean
    fun tmdbWebClient(): WebClient =
        WebClient.builder()
            .baseUrl(properties.baseUrl)
            .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer ${properties.token}")
            .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            .exchangeStrategies(
                ExchangeStrategies.builder()
                    .codecs { it.defaultCodecs().maxInMemorySize(4 * 1024 * 1024) }
                    .build()
            )
            .build()
}
