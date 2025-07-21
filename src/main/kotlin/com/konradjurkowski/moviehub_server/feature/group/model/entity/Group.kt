package com.konradjurkowski.moviehub_server.feature.group.model.entity

import com.konradjurkowski.moviehub_server.feature.user.model.entity.User
import com.konradjurkowski.moviehub_server.feature.user.model.entity.toDto
import com.konradjurkowski.moviehub_server.feature.group.model.dto.GroupDto
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import java.time.LocalDateTime

@Entity(name = "groups")
data class Group(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(nullable = false)
    val name: String,
    @Column(nullable = false)
    val description: String = "",
    @Column
    val imageUrl: String? = null,
    @Column(unique = true, nullable = false, length = 6)
    val invitationCode: String,
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "group_members",
        joinColumns = [JoinColumn(name = "group_id")],
        inverseJoinColumns = [JoinColumn(name = "user_id")],
    )
    val members: MutableSet<User> = mutableSetOf(),
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "group_admins",
        joinColumns = [JoinColumn(name = "group_id")],
        inverseJoinColumns = [JoinColumn(name = "user_id")]
    )
    val admins: MutableSet<User> = mutableSetOf(),
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "group_blacklist",
        joinColumns = [JoinColumn(name = "group_id")],
        inverseJoinColumns = [JoinColumn(name = "user_id")]
    )
    val bannedUsers: MutableSet<User> = mutableSetOf(),
    @Column
    val createdAt: LocalDateTime = LocalDateTime.now(),
    @Column
    val updatedAt: LocalDateTime = LocalDateTime.now()
)

fun Group.toDto(): GroupDto {
    return GroupDto(
        id = this.id,
        name = this.name,
        description = this.description,
        imageUrl = this.imageUrl,
        invitationCode = this.invitationCode,
        members = members.map { it.toDto() }.toList(),
        admins = admins.map { it.toDto() }.toList(),
        bannedUsers = bannedUsers.map { it.toDto() }.toList(),
    )
}
