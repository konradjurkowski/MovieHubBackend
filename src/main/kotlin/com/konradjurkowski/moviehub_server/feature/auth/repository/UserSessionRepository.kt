package com.konradjurkowski.moviehub_server.feature.auth.repository

import com.konradjurkowski.moviehub_server.feature.auth.model.entity.UserSession
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserSessionRepository : JpaRepository<UserSession, Long> {
    fun findByRefreshTokenHash(refreshTokenHash: String): UserSession?
    fun deleteByRefreshTokenHash(refreshTokenHash: String)
    fun deleteByUserId(userId: Long)
}
