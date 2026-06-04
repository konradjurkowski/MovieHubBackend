package com.konradjurkowski.moviehub_server.feature.auth.model.dto.password_reset

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class ResetPasswordRequest(
    @field:NotBlank
    @field:Email
    val email: String,
    @field:Size(min = 8, max = 72)
    val password: String,
    @field:NotBlank
    val code: String,
)
