package com.konradjurkowski.moviehub_server.feature.user.model.dto.details

import com.konradjurkowski.moviehub_server.core.model.dto.response.ApiResponse
import com.konradjurkowski.moviehub_server.feature.user.model.dto.UserDto

data class UserDetailsResponse(val user: UserDto): ApiResponse
