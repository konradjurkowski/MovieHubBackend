package com.konradjurkowski.moviehub_server.feature.auth.model.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.Table
import java.time.Instant

enum class VerificationTokenType {
    ACCOUNT_ACTIVATION, PASSWORD_RESET,
}

@Entity
@Table(
    name = "verification_tokens",
    indexes = [
        Index(columnList = "user_id, type", unique = true),
        Index(columnList = "expiresAt"),
    ],
)
class VerificationToken(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(name = "user_id", nullable = false, updatable = false)
    val userId: Long,
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, updatable = false)
    val type: VerificationTokenType,
    @Column(nullable = false, length = 100)
    var tokenHash: String,
    @Column(nullable = false)
    var expiresAt: Instant,
    @Column(nullable = false)
    var attempts: Int = 0,
    @Column(nullable = false)
    var maxAttempts: Int = 5,
    @Column(nullable = false)
    var lastSentAt: Instant = Instant.now(),
)
