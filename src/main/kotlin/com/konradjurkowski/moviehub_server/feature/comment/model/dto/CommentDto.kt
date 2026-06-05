package com.konradjurkowski.moviehub_server.feature.comment.model.dto

import com.konradjurkowski.moviehub_server.feature.comment.model.entity.MediaType
import java.time.Instant

data class CommentDto(
    val id: Long,
    val author: AuthorDto,
    val mediaType: MediaType,
    val mediaId: Long,
    val parentId: Long? = null,
    val content: String,
    val imageUrl: String? = null,
    val likeCount: Long,
    val likedByMe: Boolean,
    val replyCount: Long,
    val edited: Boolean,
    val createdAt: Instant,
)
