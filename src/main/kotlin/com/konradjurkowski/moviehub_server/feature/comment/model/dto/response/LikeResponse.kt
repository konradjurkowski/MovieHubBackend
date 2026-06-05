package com.konradjurkowski.moviehub_server.feature.comment.model.dto.response

data class LikeResponse(
    val liked: Boolean,
    val likeCount: Long,
)
