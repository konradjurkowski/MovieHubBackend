package com.konradjurkowski.moviehub_server.feature.auth.model.dto.passwrd_reset

data class ResetPasswordRequest(
    val email: String,
    val password: String,
    val code: String,
)
