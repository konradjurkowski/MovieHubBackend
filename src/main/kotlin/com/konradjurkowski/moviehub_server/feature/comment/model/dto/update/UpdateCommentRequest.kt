package com.konradjurkowski.moviehub_server.feature.comment.model.dto.update

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class UpdateCommentRequest(
    @field:NotBlank
    @field:Size(max = 2000)
    val content: String,
    val imageUrl: String? = null,
    val imagePublicId: String? = null,
)
