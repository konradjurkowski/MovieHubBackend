package com.konradjurkowski.moviehub_server.feature.user.controller

import com.konradjurkowski.moviehub_server.core.model.dto.ApiResponse
import com.konradjurkowski.moviehub_server.core.utils.ApiHandler
import com.konradjurkowski.moviehub_server.feature.user.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/user")
class UserController(
    private val userService: UserService,
) {

    @GetMapping
    fun getUserDetails(): ResponseEntity<ApiResponse> {
        return ApiHandler.execute { userService.getUserDetails() }
    }
}
