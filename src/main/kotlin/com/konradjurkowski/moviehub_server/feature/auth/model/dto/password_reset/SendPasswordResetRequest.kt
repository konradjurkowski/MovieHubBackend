package com.konradjurkowski.moviehub_server.feature.auth.model.dto.password_reset

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class SendPasswordResetRequest(
    @field:NotBlank
    @field:Email
    val email: String,
)
