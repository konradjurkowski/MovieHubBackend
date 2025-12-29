package com.konradjurkowski.moviehub_server.core.data.api.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.konradjurkowski.moviehub_server.core.model.dto.media.SpokenLanguageDto

@JsonIgnoreProperties(ignoreUnknown = true)
data class TmdbSpokenLanguage(
    val name: String,
    val iso_639_1: String,
    @param:JsonProperty("english_name")
    val englishName: String,
)

fun TmdbSpokenLanguage.toDto(): SpokenLanguageDto {
    return SpokenLanguageDto(
        name = name,
        iso_639_1 = iso_639_1,
        englishName = englishName,
    )
}
