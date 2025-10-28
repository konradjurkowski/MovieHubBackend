package com.konradjurkowski.moviehub_server.feature.auth.service

import com.konradjurkowski.moviehub_server.core.model.dto.response.ErrorCode
import com.konradjurkowski.moviehub_server.core.service.email.EmailRequestFactory
import com.konradjurkowski.moviehub_server.core.service.email.EmailService
import com.konradjurkowski.moviehub_server.core.utils.exceptions.ApiException
import com.konradjurkowski.moviehub_server.feature.auth.model.entity.ActivationCode
import com.konradjurkowski.moviehub_server.feature.auth.repository.ActivationCodeRepository
import com.konradjurkowski.moviehub_server.feature.user.model.entity.User
import com.konradjurkowski.moviehub_server.feature.user.model.entity.UserStatus
import jakarta.transaction.Transactional
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import java.security.SecureRandom
import java.time.Instant
import java.time.temporal.ChronoUnit

@Component
@EnableScheduling
class ActivationCleanupJob(
    private val repository: ActivationCodeRepository,
) {

    @Scheduled(cron = "0 0/10 * * * *")
    fun clean() { repository.deleteExpired(Instant.now()) }
}

@Service
class ActivationCodeService(
    private val emailService: EmailService,
    private val repository: ActivationCodeRepository,
    private val passwordEncoder: BCryptPasswordEncoder,
) {

    private companion object {
        const val CODE_LIFETIME_MINUTES = 15L
        const val RESEND_COOLDOWN_SECONDS = 60L
    }

    private val secureRandom = SecureRandom()

    @Transactional
    fun createCode(user: User) {
        repository.findByUserId(user.id)?.let { activationCode ->
            val resendAllowedAt = Instant.now().minusSeconds(RESEND_COOLDOWN_SECONDS)
            if (activationCode.lastSentAt?.isAfter(resendAllowedAt) == true) {
                throw ApiException(ErrorCode.TOO_MANY_REQUESTS)
            }

            repository.deleteByUserId(user.id)
            repository.flush()
        }

        val code = "%06d".format(secureRandom.nextInt(1_000_000))
        val codeHash = passwordEncoder.encode(code)

        val activationCode = ActivationCode(
            user = user,
            codeHash = codeHash,
            expiresAt = Instant.now().plus(CODE_LIFETIME_MINUTES, ChronoUnit.MINUTES),
            attempts = 0,
            maxAttempts = 5,
            lastSentAt = Instant.now(),
        )

        val emailRequest = EmailRequestFactory.createUserActivationRequest(user, formatForHumans(code))
        emailService.send(emailRequest)

        repository.save(activationCode)
    }

    @Transactional
    fun verifyCode(user: User, rawCodeInput: String): Boolean {
        val activationCode = repository.findByUserId(user.id)
            ?: throw ApiException(ErrorCode.INVALID_ACTIVATION_CODE)

        if (activationCode.expiresAt.isBefore(Instant.now()))
            throw ApiException(ErrorCode.ACTIVATION_CODE_EXPIRED)

        if (activationCode.attempts >= activationCode.maxAttempts)
            throw ApiException(ErrorCode.TOO_MANY_ATTEMPTS)

        val normalized = normalizeInput(rawCodeInput)
        val isCodeValid = passwordEncoder.matches(normalized, activationCode.codeHash)

        if (!isCodeValid) {
            activationCode.attempts += 1
            return false
        }

        activationCode.user.status = UserStatus.ACTIVE
        repository.delete(activationCode)
        return true
    }

    private fun normalizeInput(input: String) = input.filter { it.isDigit() }
    private fun formatForHumans(code: String) = code.chunked(3).joinToString(" ")
}
