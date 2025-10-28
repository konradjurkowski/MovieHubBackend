package com.konradjurkowski.moviehub_server.core.model

data class EmailRequest(
    val recipient: String,
    val subject: String,
    val content: String? = null,
)
