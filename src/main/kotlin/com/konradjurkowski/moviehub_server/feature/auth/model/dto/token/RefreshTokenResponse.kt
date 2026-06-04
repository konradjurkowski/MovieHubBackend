package com.konradjurkowski.moviehub_server.feature.auth.model.dto.token


data class RefreshTokenResponse(
    val accessToken: String,
    val refreshToken: String,
    val expiresIn: Long,
)
