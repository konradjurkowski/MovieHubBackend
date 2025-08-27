package com.konradjurkowski.moviehub_server.feature.auth.service

import com.konradjurkowski.moviehub_server.core.config.JwtProperties
import com.konradjurkowski.moviehub_server.core.model.ClientInfo
import com.konradjurkowski.moviehub_server.feature.user.model.entity.User
import com.konradjurkowski.moviehub_server.feature.auth.model.entity.UserSession
import com.konradjurkowski.moviehub_server.feature.user.repository.UserRepository
import com.konradjurkowski.moviehub_server.feature.auth.repository.UserSessionRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class TokenService(
    private val jwtProperties: JwtProperties,
    private val jwtService: JwtService,
    private val userRepository: UserRepository,
    private val userSessionRepository: UserSessionRepository,
) {

    fun createTokens(user: User, clientInfo: ClientInfo): Pair<String, String> {
        val accessToken = jwtService.generateAccessToken(user = user)
        val refreshToken = jwtService.generateRefreshToken()

        val session = UserSession(
            userId = user.id,
            refreshToken = refreshToken,
            deviceInfo = clientInfo.deviceInfo,
            ipAddress = clientInfo.ipAddress,
            expiresAt = Instant.now().plusMillis(jwtProperties.refreshTokenExpiration),
        )

        userSessionRepository.save(session)
        return accessToken to refreshToken
    }

    fun refreshTokens(refreshToken: String): Pair<String, String>? {
        val session = userSessionRepository.findByRefreshToken(refreshToken) ?: return null

        if (session.expiresAt.isBefore(Instant.now())) {
            userSessionRepository.deleteByRefreshToken(refreshToken)
            return null
        }

        val user = userRepository.findById(session.userId).orElse(null) ?: return null

        val newAccessToken = jwtService.generateAccessToken(user = user)
        val newRefreshToken = jwtService.generateRefreshToken()

        session.refreshToken = newRefreshToken
        session.lastUsedAt = Instant.now()
        session.expiresAt = Instant.now().plusMillis(jwtProperties.refreshTokenExpiration)
        userSessionRepository.save(session)
        userSessionRepository.deleteByRefreshToken(refreshToken)

        return newAccessToken to newRefreshToken
    }

    @Transactional
    fun invalidateRefreshToken(refreshToken: String) {
        userSessionRepository.deleteByRefreshToken(refreshToken)
    }

    @Transactional
    fun invalidateAllUserTokens(userId: Long) {
        userSessionRepository.deleteByUserId(userId)
    }
}
