package com.konradjurkowski.moviehub_server.feature.auth.model.entity

import com.konradjurkowski.moviehub_server.feature.user.model.entity.User
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.Instant

@Entity
@Table(
    name = "activation_codes",
    indexes = [Index(columnList = "user_id", unique = true)],
)
class ActivationCode(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,
    @Column(nullable = false, length = 100)
    val codeHash: String,
    @Column(nullable = false)
    val expiresAt: Instant,
    @Column(nullable = false)
    var attempts: Int = 0,
    @Column(nullable = false)
    var maxAttempts: Int = 5,
    @Column(nullable = true)
    var lastSentAt: Instant? = null,
)