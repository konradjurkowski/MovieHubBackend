package com.konradjurkowski.moviehub_server.feature.auth.model.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDateTime

@Entity(name = "user_sessions")
data class UserSession(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(nullable = false)
    val userId: Long,
    @Column(nullable = false, unique = true)
    val refreshToken: String,
    @Column
    val deviceInfo: String? = null,
    @Column
    val ipAddress: String? = null,
    @Column(nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),
    @Column(nullable = false)
    val expiresAt: LocalDateTime,
    @Column(nullable = false)
    val lastUsedAt: LocalDateTime = LocalDateTime.now(),
)
