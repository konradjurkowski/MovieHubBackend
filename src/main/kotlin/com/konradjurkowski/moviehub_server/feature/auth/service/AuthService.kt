package com.konradjurkowski.moviehub_server.feature.auth.service

import com.konradjurkowski.moviehub_server.core.data.properties.JwtProperties
import com.konradjurkowski.moviehub_server.core.model.ClientInfo
import com.konradjurkowski.moviehub_server.core.model.dto.response.ErrorCode
import com.konradjurkowski.moviehub_server.core.utils.exceptions.ApiException
import com.konradjurkowski.moviehub_server.feature.auth.model.dto.activation.ActivateAccountRequest
import com.konradjurkowski.moviehub_server.feature.auth.model.dto.activation.SendActivationCodeRequest
import com.konradjurkowski.moviehub_server.feature.auth.model.dto.login.LoginRequest
import com.konradjurkowski.moviehub_server.feature.auth.model.dto.login.LoginResponse
import com.konradjurkowski.moviehub_server.feature.auth.model.dto.passwrd_reset.ResetPasswordRequest
import com.konradjurkowski.moviehub_server.feature.auth.model.dto.passwrd_reset.SendPasswordResetRequest
import com.konradjurkowski.moviehub_server.feature.auth.model.dto.register.RegisterRequest
import com.konradjurkowski.moviehub_server.feature.auth.model.dto.register.RegisterResponse
import com.konradjurkowski.moviehub_server.feature.auth.model.dto.token.RefreshTokenRequest
import com.konradjurkowski.moviehub_server.feature.auth.model.dto.token.RefreshTokenResponse
import com.konradjurkowski.moviehub_server.feature.user.model.entity.UserStatus
import com.konradjurkowski.moviehub_server.feature.user.model.entity.toDto
import com.konradjurkowski.moviehub_server.feature.user.service.UserService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val jwtProperties: JwtProperties,
    private val tokenService: TokenService,
    private val userService: UserService,
    private val verificationTokenService: VerificationTokenService,
    private val passwordEncoder: BCryptPasswordEncoder,
) {

    fun register(request: RegisterRequest): RegisterResponse {
        if (userService.existsByEmail(request.email)) {
            throw ApiException(errorCode = ErrorCode.EMAIL_ALREADY_EXISTS)
        }

        val user = userService.createUser(
            email = request.email,
            password = passwordEncoder.encode(request.password),
            name = request.name,
        )
        verificationTokenService.createAccountActivationToken(user)
        return RegisterResponse(user = user.toDto())
    }

    fun login(request: LoginRequest, clientInfo: ClientInfo): LoginResponse {
        val user = userService.findByEmail(request.email)
            ?: throw ApiException(errorCode = ErrorCode.INVALID_CREDENTIALS)

        if (user.status != UserStatus.ACTIVE) {
            throw ApiException(ErrorCode.ACCOUNT_NOT_ACTIVATED)
        }

        if (!passwordEncoder.matches(request.password, user.password)) {
            throw ApiException(errorCode = ErrorCode.INVALID_CREDENTIALS)
        }

        val (accessToken, refreshToken) = tokenService.createTokens(user = user, clientInfo = clientInfo)

        val userDto = userService.findUserDtoById(user.id)
            ?: throw ApiException(errorCode = ErrorCode.INVALID_CREDENTIALS)

        return LoginResponse(
            user = userDto,
            accessToken = accessToken,
            refreshToken = refreshToken,
            expiresIn = jwtProperties.accessTokenExpiration,
        )
    }

    fun sendPasswordResetCode(request: SendPasswordResetRequest) {
        val user = userService.findByEmail(request.email) ?: return

        if (user.status != UserStatus.ACTIVE) {
            throw ApiException(ErrorCode.ACCOUNT_NOT_ACTIVATED)
        }

        verificationTokenService.createPasswordResetToken(user)
    }

    fun resetPassword(request: ResetPasswordRequest) {
        val user = userService.findByEmail(request.email)
            ?: throw ApiException(ErrorCode.INVALID_RESET_PASSWORD_CODE)

        val isCodeValid = verificationTokenService.verifyPasswordResetToken(user, request.code)
        if (!isCodeValid) throw ApiException(ErrorCode.INVALID_RESET_PASSWORD_CODE)

        user.password = passwordEncoder.encode(request.password)
        userService.updateUser(user)
    }

    fun activateAccount(request: ActivateAccountRequest) {
        val user = userService.findByEmail(request.email)
            ?: throw ApiException(ErrorCode.INVALID_ACTIVATION_ACCOUNT_CODE)

        val isCodeValid = verificationTokenService.verifyAccountActivationToken(user, request.code)
        if (!isCodeValid) throw ApiException(ErrorCode.INVALID_ACTIVATION_ACCOUNT_CODE)

        user.status = UserStatus.ACTIVE
        userService.updateUser(user)
    }

    fun sendActivateAccountCode(request: SendActivationCodeRequest) {
        val user = userService.findByEmail(request.email) ?: return
        if (user.status != UserStatus.ACTIVE) {
            verificationTokenService.createAccountActivationToken(user)
        }
    }

    fun refreshToken(request: RefreshTokenRequest): RefreshTokenResponse {
        val (accessToken, refreshToken) = tokenService.refreshTokens(request.refreshToken)
            ?: throw ApiException(errorCode = ErrorCode.INVALID_REFRESH_TOKEN)

        return RefreshTokenResponse(
            accessToken = accessToken,
            refreshToken = refreshToken,
            expiresIn = jwtProperties.accessTokenExpiration,
        )
    }

    fun logout(refreshToken: String) {
        tokenService.invalidateRefreshToken(refreshToken)
    }
}
