package com.konradjurkowski.moviehub_server.feature.auth.repository

import com.konradjurkowski.moviehub_server.feature.auth.model.entity.VerificationToken
import com.konradjurkowski.moviehub_server.feature.auth.model.entity.VerificationTokenType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.Instant

@Repository
interface VerificationTokenRepository : JpaRepository<VerificationToken, Long> {
    fun findByUserIdAndType(userId: Long, type: VerificationTokenType): VerificationToken?

    @Modifying
    fun deleteByUserIdAndType(userId: Long, type: VerificationTokenType)

    @Modifying
    @Query("DELETE FROM VerificationToken t WHERE t.expiresAt <= :now")
    fun deleteExpired(now: Instant): Int
}