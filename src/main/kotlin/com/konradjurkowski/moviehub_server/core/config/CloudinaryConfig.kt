package com.konradjurkowski.moviehub_server.core.config

import com.cloudinary.Cloudinary
import com.cloudinary.utils.ObjectUtils
import com.konradjurkowski.moviehub_server.core.data.properties.CloudinaryProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CloudinaryConfig(
    private val properties: CloudinaryProperties,
) {

    @Bean
    fun cloudinary(): Cloudinary = Cloudinary(
        ObjectUtils.asMap(
            "cloud_name", properties.cloudName,
            "api_key", properties.apiKey,
            "api_secret", properties.apiSecret,
            "secure", true,
        )
    )
}
