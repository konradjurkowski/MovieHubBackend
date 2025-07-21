package com.konradjurkowski.moviehub_server.feature.auth.controller

import com.konradjurkowski.moviehub_server.core.model.dto.ApiResponse
import com.konradjurkowski.moviehub_server.core.model.toClientInfo
import com.konradjurkowski.moviehub_server.core.utils.ApiHandler
import com.konradjurkowski.moviehub_server.feature.auth.model.dto.logout.LogoutRequest
import com.konradjurkowski.moviehub_server.feature.auth.model.dto.login.LoginRequest
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
