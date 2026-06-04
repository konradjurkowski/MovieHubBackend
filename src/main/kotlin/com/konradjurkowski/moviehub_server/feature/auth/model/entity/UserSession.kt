package com.konradjurkowski.moviehub_server.feature.auth.model.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.Table
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant

@Entity
@Table(
    name = "user_sessions",
    indexes = [
        Index(name = "idx_user_sessions_user_id", columnList = "user_id"),
    ],
)
@EntityListeners(AuditingEntityListener::class)
class UserSession(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(name = "user_id", nullable = false)
    val userId: Long,
    @Column(name = "refresh_token_hash", nullable = false, unique = true)
    var refreshTokenHash: String,
    @Column(name = "device_info")
    val deviceInfo: String? = null,
    @Column(name = "ip_address")
    val ipAddress: String? = null,
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: Instant = Instant.now(),
    @Column(name = "expires_at", nullable = false)
    var expiresAt: Instant,
    @Column(name = "last_used_at", nullable = false)
    var lastUsedAt: Instant = Instant.now(),
) {
    override fun equals(other: Any?): Boolean =
        this === other || (other is UserSession && id != 0L && id == other.id)

    override fun hashCode(): Int = id.hashCode()
}
