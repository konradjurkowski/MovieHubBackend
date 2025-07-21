package com.konradjurkowski.moviehub_server.feature.group.service

import com.konradjurkowski.moviehub_server.core.config.SecurityService
import com.konradjurkowski.moviehub_server.core.model.dto.ErrorCode
import com.konradjurkowski.moviehub_server.core.utils.exceptions.ApiException
import com.konradjurkowski.moviehub_server.feature.group.model.dto.create.CreateGroupRequest
import com.konradjurkowski.moviehub_server.feature.group.model.dto.create.CreateGroupResponse
import com.konradjurkowski.moviehub_server.feature.group.model.dto.join.JoinGroupResponse
import com.konradjurkowski.moviehub_server.feature.group.model.dto.leave.LeaveGroupResponse
import com.konradjurkowski.moviehub_server.feature.group.model.entity.Group
import com.konradjurkowski.moviehub_server.feature.group.model.entity.toDto
import com.konradjurkowski.moviehub_server.feature.group.repository.GroupRepository
import com.konradjurkowski.moviehub_server.feature.group.utils.InvitationCodeGenerator
import org.springframework.stereotype.Service

@Service
class GroupService(
    private val groupRepository: GroupRepository,
    private val securityService: SecurityService,
    private val invitationCodeGenerator: InvitationCodeGenerator,
) {

    fun createGroup(request: CreateGroupRequest): CreateGroupResponse {
        val user = securityService.getCurrentUser()
        val invitationCode = generateUniqueCode()

        val group = Group(
            name = request.name,
            description = request.description,
            imageUrl = request.imageUrl,
            invitationCode = invitationCode,
            members = mutableSetOf(user),
            admins = mutableSetOf(user),
        )
        return CreateGroupResponse(group = groupRepository.save(group).toDto())
    }

    fun joinGroup(invitationCode: String): JoinGroupResponse {
        if (!invitationCodeGenerator.isValidFormat(invitationCode)) {
            throw ApiException(ErrorCode.INVALID_INVITATION_CODE_FORMAT)
        }

        val user = securityService.getCurrentUser()
        val group = groupRepository.findByInvitationCode(invitationCode)
            ?: throw ApiException(ErrorCode.GROUP_NOT_FOUND)

        if (group.members.contains(user)) {
            throw ApiException(ErrorCode.USER_ALREADY_IN_GROUP)
        }

        group.members.add(user)
        return JoinGroupResponse(group = groupRepository.save(group).toDto())
    }

    fun leaveGroup(groupId: Long): LeaveGroupResponse {
        val user = securityService.getCurrentUser()
        val group = groupRepository.findById(groupId)
            .orElseThrow { ApiException(ErrorCode.GROUP_NOT_FOUND) }

        if (!group.members.contains(user)) {
            throw ApiException(ErrorCode.USER_NOT_IN_GROUP)
        }

        if (group.admins.contains(user) && group.admins.size == 1) {
            throw ApiException(ErrorCode.CANNOT_LEAVE_LAST_ADMIN)
        }

        group.members.remove(user)
        group.admins.remove(user)

        return LeaveGroupResponse(group = groupRepository.save(group).toDto())
    }

    fun removeGroup(groupId: Long) {
        groupRepository.deleteById(groupId)
    }

    private fun generateUniqueCode(): String {
        var attempts = 0
        while (attempts < 10) {
            val code = invitationCodeGenerator.generateCode()
            if (!groupRepository.existsByInvitationCode(code)) return code
            attempts++
        }
        throw ApiException(ErrorCode.UNABLE_TO_GENERATE_INVITATION_CODE)
    }
}
