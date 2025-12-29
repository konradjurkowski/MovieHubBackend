package com.konradjurkowski.moviehub_server.feature.auth.repository

import com.konradjurkowski.moviehub_server.feature.auth.model.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): User?
    fun existsByEmail(email: String): Boolean
}
