package com.konradjurkowski.moviehub_server.core.data.api.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.konradjurkowski.moviehub_server.core.model.dto.media.CrewDto
import com.konradjurkowski.moviehub_server.core.utils.constants.TmdbConstants

@JsonIgnoreProperties(ignoreUnknown = true)
data class TmdbCrew(
    val id: Long,
    val name: String,
    @param:JsonProperty("original_name")
    val originalName: String,
    val popularity: Double,
    @param:JsonProperty("profile_path")
    val profilePath: String? = null,
    val adult: Boolean,
    val gender: Long,
    @param:JsonProperty("known_for_department")
    val knownForDepartment: String,
    @param:JsonProperty("credit_id")
    val creditId: String,
    val department: String,
    val job: String,
)

fun TmdbCrew.toDto(): CrewDto {
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
