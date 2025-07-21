package com.konradjurkowski.moviehub_server.core.config

import com.konradjurkowski.moviehub_server.core.model.dto.ErrorCode
import com.konradjurkowski.moviehub_server.core.utils.exceptions.ApiException
import com.konradjurkowski.moviehub_server.feature.user.model.entity.User
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class SecurityService {

    fun getCurrentUser(): User {
        val authentication = SecurityContextHolder.getContext().authentication
        return authentication?.principal as? User
            ?: throw ApiException(ErrorCode.USER_NOT_AUTHENTICATED)
    }

    fun getCurrentUserId(): Long = getCurrentUser().id

    fun isAuthenticated(): Boolean {
        return runCatching { getCurrentUser() }.getOrNull() != null
    }
}
