package com.konradjurkowski.moviehub_server.core.data.api.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.konradjurkowski.moviehub_server.core.model.dto.media.CastDto
import com.konradjurkowski.moviehub_server.core.utils.constants.TmdbConstants

@JsonIgnoreProperties(ignoreUnknown = true)
data class TmdbCast(
    val id: Long,
    val name: String,
    @param:JsonProperty("original_name")
    val originalName: String,
    val character: String,
    val popularity: Double,
    @param:JsonProperty("profile_path")
    val profilePath: String? = null,
    val adult: Boolean,
    val gender: Long,
    @param:JsonProperty("known_for_department")
    val knownForDepartment: String,
    @param:JsonProperty("cast_id")
    val castId: Long,
    @param:JsonProperty("credit_id")
    val creditId: String,
    val order: Long,
)

fun TmdbCast.toDto(): CastDto {
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
