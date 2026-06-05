package com.konradjurkowski.moviehub_server

import com.konradjurkowski.moviehub_server.core.data.properties.CloudinaryProperties
import com.konradjurkowski.moviehub_server.core.data.properties.JwtProperties
import com.konradjurkowski.moviehub_server.core.data.properties.TmdbProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(JwtProperties::class, TmdbProperties::class, CloudinaryProperties::class)
class MovieHubServerApplication

fun main(args: Array<String>) {
	runApplication<MovieHubServerApplication>(*args)
}
