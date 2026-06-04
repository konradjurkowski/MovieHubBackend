package com.konradjurkowski.moviehub_server.feature.auth.service

import com.konradjurkowski.moviehub_server.core.data.properties.JwtProperties
import com.konradjurkowski.moviehub_server.core.model.ClientInfo
import com.konradjurkowski.moviehub_server.feature.auth.model.entity.User
import com.konradjurkowski.moviehub_server.feature.auth.model.entity.UserSession
import com.konradjurkowski.moviehub_server.feature.auth.repository.UserRepository
import com.konradjurkowski.moviehub_server.feature.auth.repository.UserSessionRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.security.MessageDigest
import java.time.Instant

@Service
class AuthTokenService(
    private val jwtProperties: JwtProperties,
    private val jwtService: JwtService,
    private val userRepository: UserRepository,
    private val userSessionRepository: UserSessionRepository,
) {

    @Transactional
    fun createTokens(user: User, clientInfo: ClientInfo): Pair<String, String> {
        val accessToken = jwtService.generateAccessToken(user = user)
        val refreshToken = jwtService.generateRefreshToken()

        val session = UserSession(
            userId = user.id,
            refreshTokenHash = refreshToken.sha256(),
            deviceInfo = clientInfo.deviceInfo,
            ipAddress = clientInfo.ipAddress,
            expiresAt = Instant.now().plusMillis(jwtProperties.refreshTokenExpiration),
        )

        userSessionRepository.save(session)
        return accessToken to refreshToken
    }

    @Transactional
    fun refreshTokens(refreshToken: String): Pair<String, String>? {
        val session = userSessionRepository.findByRefreshTokenHash(refreshToken.sha256()) ?: return null

        if (session.expiresAt.isBefore(Instant.now())) {
            userSessionRepository.delete(session)
            return null
        }

        val user = userRepository.findById(session.userId).orElse(null) ?: return null

        val newAccessToken = jwtService.generateAccessToken(user = user)
        val newRefreshToken = jwtService.generateRefreshToken()

        session.refreshTokenHash = newRefreshToken.sha256()
        session.lastUsedAt = Instant.now()
        session.expiresAt = Instant.now().plusMillis(jwtProperties.refreshTokenExpiration)
        userSessionRepository.save(session)

        return newAccessToken to newRefreshToken
    }

    @Transactional
    fun invalidateRefreshToken(refreshToken: String) {
        userSessionRepository.deleteByRefreshTokenHash(refreshToken.sha256())
    }

    @Transactional
    fun invalidateAllUserTokens(userId: Long) {
        userSessionRepository.deleteByUserId(userId)
    }

    private fun String.sha256(): String =
        MessageDigest.getInstance("SHA-256")
            .digest(toByteArray())
            .joinToString(separator = "") { "%02x".format(it) }
}
