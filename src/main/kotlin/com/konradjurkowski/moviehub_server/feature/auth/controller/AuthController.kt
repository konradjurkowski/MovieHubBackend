package com.konradjurkowski.moviehub_server.feature.auth.controller

import com.konradjurkowski.moviehub_server.core.model.dto.response.ApiResponse
import com.konradjurkowski.moviehub_server.core.model.toClientInfo
import com.konradjurkowski.moviehub_server.core.utils.ApiHandler
import com.konradjurkowski.moviehub_server.feature.auth.model.dto.activation.ActivateAccountRequest
import com.konradjurkowski.moviehub_server.feature.auth.model.dto.activation.SendActivationCodeRequest
import com.konradjurkowski.moviehub_server.feature.auth.model.dto.logout.LogoutRequest
import com.konradjurkowski.moviehub_server.feature.auth.model.dto.login.LoginRequest
import com.konradjurkowski.moviehub_server.feature.auth.model.dto.passwrd_reset.ResetPasswordRequest
import com.konradjurkowski.moviehub_server.feature.auth.model.dto.passwrd_reset.SendPasswordResetRequest
import com.konradjurkowski.moviehub_server.feature.auth.model.dto.register.RegisterRequest
import com.konradjurkowski.moviehub_server.feature.auth.model.dto.token.RefreshTokenRequest
import com.konradjurkowski.moviehub_server.feature.auth.service.AuthService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthService,
) {

    @PostMapping("/login")
    fun login(
        @RequestBody request: LoginRequest,
        httpRequest: HttpServletRequest,
    ): ResponseEntity<ApiResponse> {
        return ApiHandler.execute { authService.login(request, clientInfo = httpRequest.toClientInfo()) }
    }

    @PostMapping("/register")
    fun register(@RequestBody request: RegisterRequest): ResponseEntity<ApiResponse> {
        return ApiHandler.execute(status = HttpStatus.CREATED) { authService.register(request) }
    }

    @PostMapping("/reset-password")
    fun resetPassword(@RequestBody request: ResetPasswordRequest): ResponseEntity<ApiResponse> {
        return ApiHandler.execute {
            authService.resetPassword(request)
            object : ApiResponse {}
        }
    }

    @PostMapping("/reset-password/code")
    fun sendResetPasswordCode(@RequestBody request: SendPasswordResetRequest): ResponseEntity<ApiResponse> {
        return ApiHandler.execute {
            authService.sendPasswordResetCode(request)
            object : ApiResponse {}
        }
    }

    @PostMapping("/activate")
    fun activateAccount(@RequestBody request: ActivateAccountRequest): ResponseEntity<ApiResponse> {
        return ApiHandler.execute {
            authService.activateAccount(request)
            object : ApiResponse {}
        }
    }

    @PostMapping("/activate/code")
    fun sendActivationCode(@RequestBody request: SendActivationCodeRequest): ResponseEntity<ApiResponse> {
        return ApiHandler.execute {
            authService.sendActivateAccountCode(request)
            object : ApiResponse {}
        }
    }

    @PostMapping("/refresh")
    fun refresh(@RequestBody request: RefreshTokenRequest): ResponseEntity<ApiResponse> {
        return ApiHandler.execute { authService.refreshToken(request) }
    }

    @PostMapping("/logout")
    fun logout(@RequestBody request: LogoutRequest): ResponseEntity<ApiResponse> {
        return ApiHandler.execute {
            authService.logout(request.refreshToken)
            object : ApiResponse {}
        }
    }
}
