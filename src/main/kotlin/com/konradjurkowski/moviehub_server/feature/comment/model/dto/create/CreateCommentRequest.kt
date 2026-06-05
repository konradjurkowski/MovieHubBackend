package com.konradjurkowski.moviehub_server.feature.comment.model.dto.create

import com.konradjurkowski.moviehub_server.feature.comment.model.entity.MediaType
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.Size

data class CreateCommentRequest(
    val mediaType: MediaType,
    @field:Positive
    val mediaId: Long,
    val parentId: Long? = null,
    @field:NotBlank
    @field:Size(max = 2000)
    val content: String,
    val imageUrl: String? = null,
    val imagePublicId: String? = null,
)
