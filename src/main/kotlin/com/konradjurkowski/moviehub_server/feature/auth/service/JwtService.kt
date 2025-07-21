package com.konradjurkowski.moviehub_server.feature.auth.service

import com.konradjurkowski.moviehub_server.core.config.JwtProperties
import com.konradjurkowski.moviehub_server.feature.user.model.entity.User
import com.konradjurkowski.moviehub_server.feature.user.model.entity.UserRole
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Service
import java.util.Date
import java.util.UUID

@Service
class JwtService(
    private val jwtProperties: JwtProperties,
) {

    private val secretKey = Keys.hmacShaKeyFor(jwtProperties.secret.toByteArray())

    fun generateAccessToken(user: User): String {
        val now = Date()
        val expiryDate = Date(now.time + jwtProperties.accessTokenExpiration)
        return Jwts.builder()
            .subject(user.id.toString())
            .claim("email", user.email)
            .claim("role", user.role.name)
            .issuedAt(now)
            .expiration(expiryDate)
            .signWith(secretKey, Jwts.SIG.HS256)
            .compact()
    }

    fun generateRefreshToken(): String {
        return UUID.randomUUID().toString()
    }

    fun validateToken(token: String): Boolean {
        return parseAllClaims(token) != null
    }

    fun getUserIdFromToken(token: String): Long? {
        return parseAllClaims(token)?.subject?.toLongOrNull()
    }

    fun getEmailFromToken(token: String): String? {
        return parseAllClaims(token)?.get("email", String::class.java)
    }

    fun getRoleFromToken(token: String): UserRole {
        val rawValue = parseAllClaims(token)?.get("role") as? String
        return UserRole.entries.firstOrNull { it.name == rawValue } ?: UserRole.USER
    }

    private fun parseAllClaims(token: String): Claims? {
        return runCatching {
            Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .payload
        }.getOrNull()
    }
}
