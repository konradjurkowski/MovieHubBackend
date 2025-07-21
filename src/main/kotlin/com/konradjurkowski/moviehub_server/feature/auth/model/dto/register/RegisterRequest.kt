package com.konradjurkowski.moviehub_server.feature.auth.model.dto.register

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String,
)
