package com.konradjurkowski.moviehub_server.feature.auth.service

import com.konradjurkowski.moviehub_server.core.config.JwtProperties
import com.konradjurkowski.moviehub_server.core.model.ClientInfo
import com.konradjurkowski.moviehub_server.core.model.dto.ErrorCode
import com.konradjurkowski.moviehub_server.core.utils.exceptions.ApiException
import com.konradjurkowski.moviehub_server.feature.auth.model.dto.login.LoginRequest
import com.konradjurkowski.moviehub_server.feature.auth.model.dto.login.LoginResponse
import com.konradjurkowski.moviehub_server.feature.auth.model.dto.register.RegisterRequest
import com.konradjurkowski.moviehub_server.feature.auth.model.dto.register.RegisterResponse
import com.konradjurkowski.moviehub_server.feature.auth.model.dto.token.RefreshTokenRequest
import com.konradjurkowski.moviehub_server.feature.auth.model.dto.token.RefreshTokenResponse
import com.konradjurkowski.moviehub_server.feature.user.model.entity.toDto
import com.konradjurkowski.moviehub_server.feature.user.service.UserService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val jwtProperties: JwtProperties,
    private val tokenService: TokenService,
    private val userService: UserService,
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
        return RegisterResponse(user = user.toDto())
    }

    fun login(request: LoginRequest, clientInfo: ClientInfo): LoginResponse {
        val user = userService.findByEmail(request.email)
            ?: throw ApiException(errorCode = ErrorCode.INVALID_CREDENTIALS)

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
