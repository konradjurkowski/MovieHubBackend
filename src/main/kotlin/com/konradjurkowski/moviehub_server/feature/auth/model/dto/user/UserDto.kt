package com.konradjurkowski.moviehub_server.feature.auth.model.dto.user

import com.konradjurkowski.moviehub_server.core.model.dto.response.ApiResponse
import com.konradjurkowski.moviehub_server.feature.auth.model.entity.UserRole

data class UserDto(
    val id: Long,
    val email: String,
    val name: String,
    val description: String = "",
    val imageUrl: String? = null,
    val role: UserRole = UserRole.USER,
) : ApiResponse
