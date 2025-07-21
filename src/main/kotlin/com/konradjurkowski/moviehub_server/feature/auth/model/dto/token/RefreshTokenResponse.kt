package com.konradjurkowski.moviehub_server.feature.auth.model.dto.token

import com.konradjurkowski.moviehub_server.core.model.dto.ApiResponse

data class RefreshTokenResponse(
    val accessToken: String,
    val refreshToken: String,
    val expiresIn: Long,
) : ApiResponse
