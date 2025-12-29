package com.konradjurkowski.moviehub_server.core.data.api.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.konradjurkowski.moviehub_server.core.model.dto.media.GenreDto

@JsonIgnoreProperties(ignoreUnknown = true)
data class TmdbGenre(
    val id: Long,
    val name: String,
)

fun TmdbGenre.toDto(): GenreDto {
    return GenreDto(
        id = id,
        name = name,
    )
}
