package com.konradjurkowski.moviehub_server.core.data.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "tmdb")
data class TmdbProperties(
    val baseUrl: String,
    val token: String,
)
