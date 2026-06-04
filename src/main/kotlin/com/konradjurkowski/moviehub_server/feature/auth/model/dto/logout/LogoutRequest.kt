package com.konradjurkowski.moviehub_server.feature.auth.model.dto.logout

import jakarta.validation.constraints.NotBlank

data class LogoutRequest(
    @field:NotBlank
    val refreshToken: String,
)
