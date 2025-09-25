package com.konradjurkowski.moviehub_server.core.model.dto.tmdb

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.konradjurkowski.moviehub_server.core.model.dto.internal.CastDto
import com.konradjurkowski.moviehub_server.core.utils.constants.TmdbConstants

@JsonIgnoreProperties(ignoreUnknown = true)
data class TmdbCastDto(
    val id: Long,
    val name: String,
    @JsonProperty("original_name")
    val originalName: String,
    val character: String,
    val popularity: Double,
    @JsonProperty("profile_path")
    val profilePath: String?,
    val adult: Boolean,
    val gender: Long,
    @JsonProperty("known_for_department")
    val knownForDepartment: String,
    @JsonProperty("cast_id")
    val castId: Long,
    @JsonProperty("credit_id")
    val creditId: String,
    val order: Long,
)

fun TmdbCastDto.toDomain(): CastDto {
    return CastDto(
        id = id,
        name = name,
        originalName = originalName,
        character = character,
        popularity = popularity,
        imageUrl = profilePath?.let { TmdbConstants.IMAGE_BASE_URL + it },
        adult = adult,
        gender = gender,
        order = order,
    )
}
