package com.konradjurkowski.moviehub_server.feature.auth.model.dto.login

import com.konradjurkowski.moviehub_server.core.model.dto.ApiResponse
import com.konradjurkowski.moviehub_server.feature.user.model.dto.UserDto

data class LoginResponse(
    val user: UserDto,
    val accessToken: String,
    val refreshToken: String,
    val expiresIn: Long,
) : ApiResponse
