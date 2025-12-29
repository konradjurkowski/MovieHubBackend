package com.konradjurkowski.moviehub_server.feature.auth.service

import com.konradjurkowski.moviehub_server.core.config.SecurityService
import com.konradjurkowski.moviehub_server.core.model.dto.response.ErrorCode
import com.konradjurkowski.moviehub_server.core.utils.exceptions.ApiException
import com.konradjurkowski.moviehub_server.feature.auth.model.entity.User
import com.konradjurkowski.moviehub_server.feature.auth.model.entity.toDto
import com.konradjurkowski.moviehub_server.feature.auth.repository.UserRepository
import com.konradjurkowski.moviehub_server.feature.auth.model.dto.user.UserDto
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrElse

@Service
class UserService(
    private val repository: UserRepository,
    private val securityService: SecurityService,
) {

    fun existsByEmail(email: String): Boolean {
        return repository.existsByEmail(email)
    }

    fun findByEmail(email: String): User? {
        return repository.findByEmail(email)
    }

    fun createUser(email: String, password: String, name: String): User {
        val user = User(
            email = email,
            password = password,
            name = name,
        )
        return repository.save(user)
    }

    fun updateUser(user: User) {
        repository.save(user)
    }

    fun getCurrentUser(): UserDto {
        val userId = securityService.getCurrentUser().id
        return repository.findById(userId)
            .getOrElse { throw ApiException(ErrorCode.USER_NOT_FOUND) }
            .toDto()
    }
}
