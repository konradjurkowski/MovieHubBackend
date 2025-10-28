package com.konradjurkowski.moviehub_server.feature.auth.repository

import com.konradjurkowski.moviehub_server.feature.auth.model.entity.ActivationCode
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.Instant

@Repository
interface ActivationCodeRepository : JpaRepository<ActivationCode, Long> {
    fun findByUserId(userId: Long): ActivationCode?
    @Modifying
    fun deleteByUserId(userId: Long)
    @Modifying
    @Query("DELETE FROM ActivationCode c WHERE c.expiresAt <= :now")
    fun deleteExpired(now: Instant): Int
}
