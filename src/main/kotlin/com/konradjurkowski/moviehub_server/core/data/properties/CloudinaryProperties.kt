package com.konradjurkowski.moviehub_server.core.data.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "cloudinary")
data class CloudinaryProperties(
    val cloudName: String,
    val apiKey: String,
    val apiSecret: String,
)
