package com.konradjurkowski.moviehub_server.feature.auth.model.dto.token

import jakarta.validation.constraints.NotBlank

data class RefreshTokenRequest(
    @field:NotBlank
    val refreshToken: String,
)
