package com.konradjurkowski.moviehub_server.feature.comment.model.dto

import com.konradjurkowski.moviehub_server.feature.auth.model.entity.User

data class AuthorDto(
    val id: Long,
    val name: String,
    val imageUrl: String? = null,
)

fun User.toAuthorDto(): AuthorDto {
    return AuthorDto(
        id = id,
        name = name,
        imageUrl = imageUrl,
    )
}
