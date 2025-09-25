package com.konradjurkowski.moviehub_server.feature.auth.model.dto.register

import com.konradjurkowski.moviehub_server.core.model.dto.response.ApiResponse
import com.konradjurkowski.moviehub_server.feature.user.model.dto.UserDto

data class RegisterResponse(val user: UserDto) : ApiResponse
