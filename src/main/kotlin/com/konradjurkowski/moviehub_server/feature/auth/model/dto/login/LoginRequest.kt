package com.konradjurkowski.moviehub_server.feature.auth.model.dto.login

data class LoginRequest(
    val email: String,
    val password: String,
)