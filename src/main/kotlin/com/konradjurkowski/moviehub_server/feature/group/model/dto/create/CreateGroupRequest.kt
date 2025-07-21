package com.konradjurkowski.moviehub_server.feature.group.model.dto.create

data class CreateGroupRequest(
    val name: String,
    val description: String = "",
    val imageUrl: String? = null,
)
