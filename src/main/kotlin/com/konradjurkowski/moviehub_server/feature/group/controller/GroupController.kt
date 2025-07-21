package com.konradjurkowski.moviehub_server.feature.group.controller

import com.konradjurkowski.moviehub_server.core.model.dto.ApiResponse
import com.konradjurkowski.moviehub_server.core.utils.ApiHandler
import com.konradjurkowski.moviehub_server.feature.group.model.dto.create.CreateGroupRequest
import com.konradjurkowski.moviehub_server.feature.group.model.dto.join.JoinGroupRequest
import com.konradjurkowski.moviehub_server.feature.group.model.dto.leave.LeaveGroupRequest
import com.konradjurkowski.moviehub_server.feature.group.service.GroupService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/group")
class GroupController(
    private val groupService: GroupService,
) {

    @PostMapping("/create")
    fun createGroup(@RequestBody request: CreateGroupRequest): ResponseEntity<ApiResponse> {
        return ApiHandler.execute(
            status = HttpStatus.CREATED,
            action = { groupService.createGroup(request) },
        )
    }

    @PostMapping("/join")
    fun joinGroup(@RequestBody request: JoinGroupRequest): ResponseEntity<ApiResponse> {
        return ApiHandler.execute { groupService.joinGroup(request.invitationCode) }
    }

    @PostMapping("/leave")
    fun leaveGroup(@RequestBody request: LeaveGroupRequest): ResponseEntity<ApiResponse> {
        return ApiHandler.execute { groupService.leaveGroup(request.groupId) }
    }

    @DeleteMapping("/remove/{groupId}")
    fun removeGroup(@PathVariable groupId: Long): ResponseEntity<ApiResponse> {
        return ApiHandler.execute(status = HttpStatus.CREATED) {
            groupService.removeGroup(groupId)
            object : ApiResponse {}
        }
    }
}
