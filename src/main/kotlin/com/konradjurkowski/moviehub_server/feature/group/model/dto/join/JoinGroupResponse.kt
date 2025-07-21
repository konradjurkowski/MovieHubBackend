package com.konradjurkowski.moviehub_server.feature.group.model.dto.join

import com.konradjurkowski.moviehub_server.core.model.dto.ApiResponse
import com.konradjurkowski.moviehub_server.feature.group.model.dto.GroupDto

data class JoinGroupResponse(val group: GroupDto) : ApiResponse
