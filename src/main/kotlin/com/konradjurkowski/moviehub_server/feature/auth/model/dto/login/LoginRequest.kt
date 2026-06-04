package com.konradjurkowski.moviehub_server.feature.auth.model.dto.login

import jakarta.validation.constraints.NotBlank

data class LoginRequest(
    @field:NotBlank
    val email: String,
    @field:NotBlank
    val password: String,
)
