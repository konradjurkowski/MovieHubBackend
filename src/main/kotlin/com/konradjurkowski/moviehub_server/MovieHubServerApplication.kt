package com.konradjurkowski.moviehub_server

import com.konradjurkowski.moviehub_server.core.config.JwtProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(JwtProperties::class)
class MovieHubServerApplication

fun main(args: Array<String>) {
	runApplication<MovieHubServerApplication>(*args)
}
