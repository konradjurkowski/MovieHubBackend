package com.konradjurkowski.moviehub_server.feature.group.model.dto

import com.konradjurkowski.moviehub_server.feature.user.model.dto.UserDto

data class GroupDto(
    val id: Long,
    val name: String,
    val description: String = "",
    val imageUrl: String? = null,
    val invitationCode: String,
    val members: List<UserDto>,
    val admins: List<UserDto>,
    val bannedUsers: List<UserDto>,
)
