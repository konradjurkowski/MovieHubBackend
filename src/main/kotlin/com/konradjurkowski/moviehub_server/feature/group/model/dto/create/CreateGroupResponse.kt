package com.konradjurkowski.moviehub_server.feature.group.model.dto.create

import com.konradjurkowski.moviehub_server.core.model.dto.response.ApiResponse
import com.konradjurkowski.moviehub_server.feature.group.model.dto.GroupDto

data class CreateGroupResponse(val group: GroupDto): ApiResponse
