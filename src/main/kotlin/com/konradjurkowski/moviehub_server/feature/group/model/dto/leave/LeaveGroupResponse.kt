package com.konradjurkowski.moviehub_server.feature.group.model.dto.leave

import com.konradjurkowski.moviehub_server.core.model.dto.response.ApiResponse
import com.konradjurkowski.moviehub_server.feature.group.model.dto.GroupDto

data class LeaveGroupResponse(val group: GroupDto) : ApiResponse
