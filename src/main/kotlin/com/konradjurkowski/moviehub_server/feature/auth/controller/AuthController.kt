package com.konradjurkowski.moviehub_server.feature.auth.controller

import com.konradjurkowski.moviehub_server.core.model.toClientInfo
import com.konradjurkowski.moviehub_server.feature.auth.model.dto.activation.ActivateAccountRequest
import com.konradjurkowski.moviehub_server.feature.auth.model.dto.activation.SendActivationCodeRequest
import com.konradjurkowski.moviehub_server.feature.auth.model.dto.logout.LogoutRequest
import com.konradjurkowski.moviehub_server.feature.auth.model.dto.login.AuthResponse
import com.konradjurkowski.moviehub_server.feature.auth.model.dto.login.LoginRequest
import com.konradjurkowski.moviehub_server.feature.auth.model.dto.password_reset.ResetPasswordRequest
import com.konradjurkowski.moviehub_server.feature.auth.model.dto.password_reset.SendPasswordResetRequest
import com.konradjurkowski.moviehub_server.feature.auth.model.dto.register.RegisterRequest
import com.konradjurkowski.moviehub_server.feature.auth.model.dto.register.RegisterResponse
import com.konradjurkowski.moviehub_server.feature.auth.model.dto.token.RefreshTokenRequest
import com.konradjurkowski.moviehub_server.feature.auth.model.dto.token.RefreshTokenResponse
import com.konradjurkowski.moviehub_server.feature.auth.service.AuthService
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthService,
) {

    @PostMapping("/login")
    fun login(
        @Valid @RequestBody request: LoginRequest,
        httpRequest: HttpServletRequest,
    ): AuthResponse {
        return authService.login(request, clientInfo = httpRequest.toClientInfo())
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    fun register(@Valid @RequestBody request: RegisterRequest): RegisterResponse {
        return authService.register(request)
    }

    @PostMapping("/reset-password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun resetPassword(@Valid @RequestBody request: ResetPasswordRequest) {
        authService.resetPassword(request)
    }

    @PostMapping("/reset-password/code")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun sendResetPasswordCode(@Valid @RequestBody request: SendPasswordResetRequest) {
        authService.sendPasswordResetCode(request)
    }

    @PostMapping("/activate")
    fun activateAccount(
        @Valid @RequestBody request: ActivateAccountRequest,
        httpRequest: HttpServletRequest,
    ): AuthResponse {
        return authService.activateAccount(request, clientInfo = httpRequest.toClientInfo())
    }

    @PostMapping("/activate/code")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun sendActivationCode(@Valid @RequestBody request: SendActivationCodeRequest) {
        authService.sendActivateAccountCode(request)
    }

    @PostMapping("/refresh")
    fun refresh(@Valid @RequestBody request: RefreshTokenRequest): RefreshTokenResponse {
        return authService.refreshToken(request)
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun logout(@Valid @RequestBody request: LogoutRequest) {
        authService.logout(request.refreshToken)
    }
}
