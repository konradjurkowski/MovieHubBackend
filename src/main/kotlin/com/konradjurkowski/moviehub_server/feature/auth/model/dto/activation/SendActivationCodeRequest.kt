package com.konradjurkowski.moviehub_server.feature.auth.model.dto.activation

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class SendActivationCodeRequest(
    @field:NotBlank
    @field:Email
    val email: String,
)
