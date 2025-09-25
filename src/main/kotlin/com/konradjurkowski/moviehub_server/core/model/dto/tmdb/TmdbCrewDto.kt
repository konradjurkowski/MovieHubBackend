package com.konradjurkowski.moviehub_server.core.model.dto.tmdb

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.konradjurkowski.moviehub_server.core.model.dto.internal.CrewDto
import com.konradjurkowski.moviehub_server.core.utils.constants.TmdbConstants

@JsonIgnoreProperties(ignoreUnknown = true)
data class TmdbCrewDto(
    val id: Long,
    val name: String,
    @JsonProperty("original_name")
    val originalName: String,
    val popularity: Double,
    @JsonProperty("profile_path")
    val profilePath: String?,
    val adult: Boolean,
    val gender: Long,
    @JsonProperty("known_for_department")
    val knownForDepartment: String,
    @JsonProperty("credit_id")
    val creditId: String,
    val department: String,
    val job: String,
)

fun TmdbCrewDto.toDomain(): CrewDto {
    return CrewDto(
        id = id,
        name = name,
        originalName = originalName,
        popularity = popularity,
        imageUrl = profilePath?.let { TmdbConstants.IMAGE_BASE_URL + it },
        adult = adult,
        gender = gender,
        department = department,
        job = job,
    )
}
