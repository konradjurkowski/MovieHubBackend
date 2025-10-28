package com.konradjurkowski.moviehub_server.feature.auth.model.dto.activation

data class ActivateAccountRequest(
    val email: String,
    val code: String,
)
