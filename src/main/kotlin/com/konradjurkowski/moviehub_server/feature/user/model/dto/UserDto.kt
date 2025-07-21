package com.konradjurkowski.moviehub_server.feature.user.model.dto

import com.konradjurkowski.moviehub_server.feature.group.model.dto.GroupDto
import com.konradjurkowski.moviehub_server.feature.user.model.entity.UserRole

data class UserDto(
    val id: Long,
    val email: String,
    val name: String,
    val description: String = "",
    val imageUrl: String? = null,
    val role: UserRole = UserRole.USER,
    val groups: List<GroupDto>,
)
