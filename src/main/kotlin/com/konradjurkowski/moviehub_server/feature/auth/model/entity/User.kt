package com.konradjurkowski.moviehub_server.feature.auth.model.entity

import com.konradjurkowski.moviehub_server.feature.auth.model.dto.user.UserDto
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant

@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener::class)
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(nullable = false, unique = true, length = 254)
    var email: String,
    @Column(nullable = false)
    var password: String,
    @Column(nullable = false)
    var name: String,
    @Column(nullable = false)
    var description: String = "",
    @Column(name = "image_url")
    var imageUrl: String? = null,
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var role: UserRole = UserRole.USER,
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: UserStatus = UserStatus.PENDING,
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: Instant = Instant.now(),
    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    var updatedAt: Instant = Instant.now(),
) {
    override fun equals(other: Any?): Boolean =
        this === other || (other is User && id != 0L && id == other.id)

    override fun hashCode(): Int = id.hashCode()
}

enum class UserRole {
    USER, ADMIN
}

enum class UserStatus {
    PENDING, ACTIVE, SUSPENDED
}

fun User.toDto(): UserDto {
    return UserDto(
        id = this.id,
        email = this.email,
        name = this.name,
        description = this.description,
        imageUrl = this.imageUrl,
        role = this.role,
    )
}
