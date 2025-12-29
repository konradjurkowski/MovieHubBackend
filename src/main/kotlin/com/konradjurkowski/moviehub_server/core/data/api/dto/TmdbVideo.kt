package com.konradjurkowski.moviehub_server.core.data.api.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.konradjurkowski.moviehub_server.core.model.dto.media.VideoDto

@JsonIgnoreProperties(ignoreUnknown = true)
data class TmdbVideo(
    val id: String,
    val name: String,
    val key: String,
    val site: String,
    val size: Long,
    val type: String,
    val official: Boolean,
    val iso_639_1: String,
    val iso_3166_1: String,
    @param:JsonProperty("published_at")
    val publishedAt: String,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class TmdbVideosResponse(val results: List<TmdbVideo>)

fun TmdbVideo.toDto(): VideoDto {
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
