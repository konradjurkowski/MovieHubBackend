package com.konradjurkowski.moviehub_server.feature.auth.controller

import com.konradjurkowski.moviehub_server.feature.auth.model.dto.user.UserDto
import com.konradjurkowski.moviehub_server.feature.auth.service.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/user")
class UserController(
    private val userService: UserService,
) {

    @GetMapping
    fun getUserDetails(): UserDto {
        return userService.getCurrentUser()
    }
}
