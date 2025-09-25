package com.konradjurkowski.moviehub_server.core.model.dto.tmdb

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.konradjurkowski.moviehub_server.core.model.dto.internal.SpokenLanguageDto

@JsonIgnoreProperties(ignoreUnknown = true)
data class TmdbSpokenLanguageDto(
    val name: String,
    val iso_639_1: String,
    @JsonProperty("english_name")
    val englishName: String,
)

fun TmdbSpokenLanguageDto.toDomain(): SpokenLanguageDto {
    return SpokenLanguageDto(
        name = name,
        iso_639_1 = iso_639_1,
        englishName = englishName,
    )
}
