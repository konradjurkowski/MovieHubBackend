package com.konradjurkowski.moviehub_server.core.data.api.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.konradjurkowski.moviehub_server.core.model.dto.media.CreatorDto
import com.konradjurkowski.moviehub_server.core.utils.constants.TmdbConstants

@JsonIgnoreProperties(ignoreUnknown = true)
data class TmdbCreator(
    val id: Long,
    val name: String,
    @param:JsonProperty("original_name")
    val originalName: String? = null,
    val gender: Long? = null,
    @param:JsonProperty("profile_path")
    val profilePath: String? = null,
)

fun TmdbCreator.toDto(): CreatorDto {
    return CreatorDto(
        id = id,
        name = name,
        originalName = originalName,
        gender = gender,
        imageUrl = profilePath?.let { TmdbConstants.IMAGE_BASE_URL + it },
    )
}
