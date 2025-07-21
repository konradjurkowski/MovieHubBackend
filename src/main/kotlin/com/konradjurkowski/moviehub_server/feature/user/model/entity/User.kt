package com.konradjurkowski.moviehub_server.feature.user.model.entity

import com.konradjurkowski.moviehub_server.feature.group.model.dto.GroupDto
import com.konradjurkowski.moviehub_server.feature.user.model.dto.UserDto
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDateTime

@Entity(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(nullable = false, unique = true)
    val email: String,
    @Column(nullable = false)
    val password: String,
    @Column(nullable = false)
    val name: String,
    @Column(nullable = false)
    val description: String = "",
    @Column
    val imageUrl: String? = null,
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val role: UserRole = UserRole.USER,
    @Column
    val createdAt: LocalDateTime = LocalDateTime.now(),
    @Column
    val updatedAt: LocalDateTime = LocalDateTime.now(),
)

enum class UserRole {
    USER, ADMIN
}

fun User.toDto(groups: List<GroupDto> = emptyList()): UserDto {
    return UserDto(
        id = this.id,
        email = this.email,
        name = this.name,
        description = this.description,
        imageUrl = this.imageUrl,
        role = this.role,
        groups = groups,
    )
}
