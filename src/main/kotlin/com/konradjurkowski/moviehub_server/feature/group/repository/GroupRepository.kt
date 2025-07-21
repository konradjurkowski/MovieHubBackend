package com.konradjurkowski.moviehub_server.feature.group.repository

import com.konradjurkowski.moviehub_server.feature.group.model.entity.Group
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface GroupRepository : JpaRepository<Group, Long> {
    fun findByInvitationCode(invitationCode: String): Group?
    fun existsByInvitationCode(invitationCode: String): Boolean

    @Query("SELECT g FROM groups g JOIN g.members m WHERE m.id = :userId")
    fun findGroupsByUserId(userId: Long): List<Group>
}
