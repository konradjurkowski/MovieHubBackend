package com.konradjurkowski.moviehub_server.feature.comment.controller

import com.konradjurkowski.moviehub_server.core.model.dto.response.SearchResponse
import com.konradjurkowski.moviehub_server.feature.comment.model.dto.CommentDto
import com.konradjurkowski.moviehub_server.feature.comment.model.dto.create.CreateCommentRequest
import com.konradjurkowski.moviehub_server.feature.comment.model.dto.response.LikeResponse
import com.konradjurkowski.moviehub_server.feature.comment.model.dto.update.UpdateCommentRequest
import com.konradjurkowski.moviehub_server.feature.comment.model.entity.MediaType
import com.konradjurkowski.moviehub_server.feature.comment.service.CommentService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/comments")
class CommentController(
    private val commentService: CommentService,
) {

    @GetMapping
    fun getComments(
        @RequestParam mediaType: MediaType,
        @RequestParam mediaId: Long,
        @RequestParam(defaultValue = "1") page: Int,
    ): SearchResponse<CommentDto> {
        return commentService.getComments(mediaType = mediaType, mediaId = mediaId, page = page)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createComment(@Valid @RequestBody request: CreateCommentRequest): CommentDto {
        return commentService.createComment(request)
    }

    @GetMapping("/{id}/replies")
    fun getReplies(
        @PathVariable id: Long,
        @RequestParam(defaultValue = "1") page: Int,
    ): SearchResponse<CommentDto> {
        return commentService.getReplies(parentId = id, page = page)
    }

    @PostMapping("/{id}/like")
    fun toggleLike(@PathVariable id: Long): LikeResponse {
        return commentService.toggleLike(id)
    }

    @PutMapping("/{id}")
    fun updateComment(
        @PathVariable id: Long,
        @Valid @RequestBody request: UpdateCommentRequest,
    ): CommentDto {
        return commentService.updateComment(id = id, request = request)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteComment(@PathVariable id: Long) {
        commentService.deleteComment(id)
    }
}
