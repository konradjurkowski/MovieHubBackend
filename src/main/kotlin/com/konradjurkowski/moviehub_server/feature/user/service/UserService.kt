package com.konradjurkowski.moviehub_server.feature.user.service

import com.konradjurkowski.moviehub_server.core.config.SecurityService
import com.konradjurkowski.moviehub_server.core.model.dto.ErrorCode
import com.konradjurkowski.moviehub_server.core.utils.exceptions.ApiException
import com.konradjurkowski.moviehub_server.feature.group.model.entity.toDto
import com.konradjurkowski.moviehub_server.feature.group.repository.GroupRepository
import com.konradjurkowski.moviehub_server.feature.user.model.dto.UserDto
import com.konradjurkowski.moviehub_server.feature.user.model.dto.details.UserDetailsResponse
import com.konradjurkowski.moviehub_server.feature.user.model.entity.User
import com.konradjurkowski.moviehub_server.feature.user.model.entity.toDto
import com.konradjurkowski.moviehub_server.feature.user.repository.UserRepository
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrElse

@Service
class UserService(
    private val userRepository: UserRepository,
    private val groupRepository: GroupRepository,
    private val securityService: SecurityService,
) {

    fun existsByEmail(email: String): Boolean {
        return userRepository.existsByEmail(email)
    }

    fun findByEmail(email: String): User? {
        return userRepository.findByEmail(email)
    }

    fun createUser(email: String, password: String, name: String): User {
        val user = User(
            email = email,
            password = password,
            name = name,
        )
        return userRepository.save(user)
    }

    fun findUserDtoById(userId: Long): UserDto? {
        val user = userRepository.findById(userId).getOrElse { null } ?: return null
        val groups = groupRepository.findGroupsByUserId(user.id).map { it.toDto() }
        return user.toDto(groups)
    }

    fun getUserDetails(): UserDetailsResponse {
        val userId = securityService.getCurrentUser().id
        val user = findUserDtoById(userId)
            ?: throw ApiException(ErrorCode.USER_NOT_FOUND)
        return UserDetailsResponse(user = user)
    }
}
