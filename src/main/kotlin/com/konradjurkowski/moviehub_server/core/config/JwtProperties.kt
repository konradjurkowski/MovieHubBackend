package com.konradjurkowski.moviehub_server.core.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding

@ConfigurationProperties(prefix = "jwt")
data class JwtProperties @ConstructorBinding constructor(
    val secret: String,
    val accessTokenExpiration: Long,
    val refreshTokenExpiration: Long
)
