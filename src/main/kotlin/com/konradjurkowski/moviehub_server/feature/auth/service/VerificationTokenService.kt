package com.konradjurkowski.moviehub_server.feature.auth.service

import com.konradjurkowski.moviehub_server.core.model.dto.response.ErrorCode
import com.konradjurkowski.moviehub_server.core.service.email.EmailRequestFactory
import com.konradjurkowski.moviehub_server.core.service.email.EmailService
import com.konradjurkowski.moviehub_server.core.utils.exceptions.ApiException
import com.konradjurkowski.moviehub_server.feature.auth.model.entity.VerificationToken
import com.konradjurkowski.moviehub_server.feature.auth.model.entity.VerificationTokenType
import com.konradjurkowski.moviehub_server.feature.auth.repository.VerificationTokenRepository
import com.konradjurkowski.moviehub_server.feature.user.model.entity.User
import jakarta.transaction.Transactional
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.security.SecureRandom
import java.time.Instant
import java.time.temporal.ChronoUnit

@Service
class VerificationTokenService(
    private val emailService: EmailService,
    private val repository: VerificationTokenRepository,
    private val passwordEncoder: BCryptPasswordEncoder,
) {

    private companion object {
        const val CODE_LIFETIME_MINUTES = 15L
        const val RESEND_COOLDOWN_SECONDS = 60L
    }

    private val secureRandom = SecureRandom()

    @Transactional
    fun createActivationToken(user: User) =
        createToken(user, VerificationTokenType.ACCOUNT_ACTIVATION)

    @Transactional
    fun createPasswordResetToken(user: User) =
        createToken(user, VerificationTokenType.PASSWORD_RESET)

    @Transactional
    fun verifyActivationToken(user: User, rawToken: String) =
        verifyToken(user = user, type = VerificationTokenType.ACCOUNT_ACTIVATION, rawToken = rawToken)

    @Transactional
    fun verifyPasswordResetToken(user: User, rawToken: String) =
        verifyToken(user = user, type = VerificationTokenType.PASSWORD_RESET, rawToken = rawToken)

    private fun createToken(user: User, type: VerificationTokenType) {
        val token = "%06d".format(secureRandom.nextInt(1_000_000))
        val tokenHash = passwordEncoder.encode(token)

        val verificationToken = repository.findByUserIdAndType(userId = user.id, type = type)
        if (verificationToken != null) {
            val resendAllowedAt = Instant.now().minusSeconds(RESEND_COOLDOWN_SECONDS)
            if (verificationToken.lastSentAt.isAfter(resendAllowedAt)) {
                throw ApiException(ErrorCode.TOO_MANY_REQUESTS)
            }

            val expiresAt = Instant.now().plus(CODE_LIFETIME_MINUTES, ChronoUnit.MINUTES)
            verificationToken.apply {
                this.tokenHash = tokenHash
                this.expiresAt = expiresAt
                this.attempts = 0
                this.lastSentAt = Instant.now()
            }
            repository.save(verificationToken)
            sendEmail(type = type, user = user, code = token.format())
            return
        }

        val expiresAt = Instant.now().plus(CODE_LIFETIME_MINUTES, ChronoUnit.MINUTES)
        val entity = VerificationToken(
            userId = user.id,
            type = type,
            tokenHash = tokenHash,
            expiresAt = expiresAt,
        )
        repository.save(entity)
        sendEmail(type = type, user = user, code = token.format())
    }

    private fun verifyToken(user: User, type: VerificationTokenType, rawToken: String): Boolean {
        val verificationToken = repository.findByUserIdAndType(userId = user.id, type = type)
            ?: throw ApiException(ErrorCode.INVALID_ACTIVATION_CODE)

        if (verificationToken.expiresAt.isBefore(Instant.now()))
            throw ApiException(ErrorCode.ACTIVATION_CODE_EXPIRED)

        if (verificationToken.attempts >= verificationToken.maxAttempts)
            throw ApiException(ErrorCode.TOO_MANY_ATTEMPTS)

        val isCodeValid = passwordEncoder.matches(rawToken.normalize(), verificationToken.tokenHash)
        if (!isCodeValid) {
            verificationToken.attempts += 1
            return false
        }

        repository.delete(verificationToken)
        return true
    }

    private fun sendEmail(type: VerificationTokenType, user: User, code: String) {
        val request = when (type) {
            VerificationTokenType.ACCOUNT_ACTIVATION -> EmailRequestFactory.accountActivation(user, code)
            VerificationTokenType.PASSWORD_RESET -> EmailRequestFactory.passwordReset(user, code)
        }
        emailService.send(request)
    }

    private fun String.format() = this.chunked(3).joinToString(" ")
    private fun String.normalize() = this.filter { it.isDigit() }
}