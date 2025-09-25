package com.konradjurkowski.moviehub_server.core.model.dto.tmdb

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.konradjurkowski.moviehub_server.core.model.dto.internal.VideoDto

@JsonIgnoreProperties(ignoreUnknown = true)
data class TmdbVideoDto(
    val id: String,
    val name: String,
    val key: String,
    val site: String,
    val size: Long,
    val type: String,
    val official: Boolean,
    val iso_639_1: String,
    val iso_3166_1: String,
    @JsonProperty("published_at")
    val publishedAt: String,
)

fun TmdbVideoDto.toDomain(): VideoDto {
    return VideoDto(
        id = id,
        name = name,
        key = key,
        site = site,
        size = size,
        type = type,
        official = official,
        iso_639_1 = iso_639_1,
        iso_3166_1 = iso_3166_1,
        publishedAt = publishedAt,
    )
}
