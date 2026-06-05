package com.konradjurkowski.moviehub_server.feature.comment.service

import com.konradjurkowski.moviehub_server.core.config.SecurityService
import com.konradjurkowski.moviehub_server.core.model.dto.response.ErrorCode
import com.konradjurkowski.moviehub_server.core.model.dto.response.SearchResponse
import com.konradjurkowski.moviehub_server.core.service.cloudinary.CloudinaryService
import com.konradjurkowski.moviehub_server.core.utils.exceptions.ApiException
import com.konradjurkowski.moviehub_server.feature.auth.model.entity.UserRole
import com.konradjurkowski.moviehub_server.feature.auth.repository.UserRepository
import com.konradjurkowski.moviehub_server.feature.comment.model.dto.AuthorDto
import com.konradjurkowski.moviehub_server.feature.comment.model.dto.CommentDto
import com.konradjurkowski.moviehub_server.feature.comment.model.dto.create.CreateCommentRequest
import com.konradjurkowski.moviehub_server.feature.comment.model.dto.response.LikeResponse
import com.konradjurkowski.moviehub_server.feature.comment.model.dto.toAuthorDto
import com.konradjurkowski.moviehub_server.feature.comment.model.dto.update.UpdateCommentRequest
import com.konradjurkowski.moviehub_server.feature.comment.model.entity.Comment
import com.konradjurkowski.moviehub_server.feature.comment.model.entity.CommentLike
import com.konradjurkowski.moviehub_server.feature.comment.model.entity.MediaType
import com.konradjurkowski.moviehub_server.feature.comment.repository.CommentLikeRepository
import com.konradjurkowski.moviehub_server.feature.comment.repository.CommentRepository
import com.konradjurkowski.moviehub_server.feature.movie.repository.MovieRepository
import com.konradjurkowski.moviehub_server.feature.series.repository.SeriesRepository
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@Service
class CommentService(
    private val commentRepository: CommentRepository,
    private val commentLikeRepository: CommentLikeRepository,
    private val userRepository: UserRepository,
    private val movieRepository: MovieRepository,
    private val seriesRepository: SeriesRepository,
    private val securityService: SecurityService,
    private val cloudinaryService: CloudinaryService,
) {

    @Transactional(readOnly = true)
    fun getComments(mediaType: MediaType, mediaId: Long, page: Int = 1): SearchResponse<CommentDto> {
        val pageNumber = (page.coerceAtLeast(1) - 1)
        val pageable = PageRequest.of(
            pageNumber,
            PAGE_SIZE,
            Sort.by(Sort.Direction.DESC, "createdAt"),
        )
        val commentsPage = commentRepository.findByMediaTypeAndMediaIdAndParentIsNull(mediaType, mediaId, pageable)
        return SearchResponse(
            page = (commentsPage.number + 1).toLong(),
            results = assemble(commentsPage.content, includeReplyCount = true),
            totalPages = commentsPage.totalPages.toLong(),
            totalResults = commentsPage.totalElements,
        )
    }

    @Transactional(readOnly = true)
    fun getReplies(parentId: Long, page: Int = 1): SearchResponse<CommentDto> {
        if (!commentRepository.existsById(parentId)) {
            throw ApiException(ErrorCode.COMMENT_NOT_FOUND)
        }
        val pageNumber = (page.coerceAtLeast(1) - 1)
        val pageable = PageRequest.of(
            pageNumber,
            PAGE_SIZE,
            Sort.by(Sort.Direction.ASC, "createdAt"),
        )
        val repliesPage = commentRepository.findByParentId(parentId, pageable)
        return SearchResponse(
            page = (repliesPage.number + 1).toLong(),
            results = assemble(repliesPage.content, includeReplyCount = false),
            totalPages = repliesPage.totalPages.toLong(),
            totalResults = repliesPage.totalElements,
        )
    }

    @Transactional
    fun createComment(request: CreateCommentRequest): CommentDto {
        val userId = securityService.getCurrentUserId()
        validateMediaExists(request.mediaType, request.mediaId)

        // Re-parenting (styl Instagram): odpowiedz na odpowiedz laduje pod komentarzem najwyzszego poziomu,
        // dzieki czemu zagniezdzenie nigdy nie przekracza 1 poziomu.
        val parent = request.parentId?.let { parentId ->
            val target = commentRepository.findById(parentId)
                .orElseThrow { ApiException(ErrorCode.COMMENT_NOT_FOUND) }
            if (target.mediaType != request.mediaType || target.mediaId != request.mediaId) {
                throw ApiException(ErrorCode.COMMENT_NOT_FOUND)
            }
            target.parent ?: target
        }

        val comment = commentRepository.save(
            Comment(
                mediaType = request.mediaType,
                mediaId = request.mediaId,
                authorId = userId,
                content = request.content,
                imageUrl = request.imageUrl,
                imagePublicId = request.imagePublicId,
                parent = parent,
            )
        )
        return assemble(listOf(comment), includeReplyCount = false).first()
    }

    @Transactional
    fun updateComment(id: Long, request: UpdateCommentRequest): CommentDto {
        val userId = securityService.getCurrentUserId()
        val comment = commentRepository.findById(id)
            .orElseThrow { ApiException(ErrorCode.COMMENT_NOT_FOUND) }
        if (comment.authorId != userId) {
            throw ApiException(ErrorCode.NOT_COMMENT_OWNER)
        }

        val oldPublicId = comment.imagePublicId
        if (oldPublicId != null && oldPublicId != request.imagePublicId) {
            cloudinaryService.destroy(oldPublicId)
        }

        comment.content = request.content
        comment.imageUrl = request.imageUrl
        comment.imagePublicId = request.imagePublicId
        comment.edited = true
        comment.updatedAt = Instant.now()
        val saved = commentRepository.save(comment)
        return assemble(listOf(saved), includeReplyCount = true).first()
    }

    @Transactional
    fun toggleLike(commentId: Long): LikeResponse {
        val userId = securityService.getCurrentUserId()
        if (!commentRepository.existsById(commentId)) {
            throw ApiException(ErrorCode.COMMENT_NOT_FOUND)
        }

        val existingLike = commentLikeRepository.findByUserIdAndCommentId(userId, commentId)
        val liked = if (existingLike != null) {
            commentLikeRepository.delete(existingLike)
            false
        } else {
            try {
                commentLikeRepository.save(CommentLike(userId = userId, commentId = commentId))
                true
            } catch (exception: DataIntegrityViolationException) {
                // Wyscig na unique constraint (user_id, comment_id) - lajk juz istnieje.
                true
            }
        }
        return LikeResponse(liked = liked, likeCount = commentLikeRepository.countByCommentId(commentId))
    }

    @Transactional
    fun deleteComment(id: Long) {
        val currentUser = securityService.getCurrentUser()
        val comment = commentRepository.findById(id)
            .orElseThrow { ApiException(ErrorCode.COMMENT_NOT_FOUND) }
        if (comment.authorId != currentUser.id && currentUser.role != UserRole.ADMIN) {
            throw ApiException(ErrorCode.NOT_COMMENT_OWNER)
        }

        val replies = if (comment.parent == null) commentRepository.findAllByParentId(id) else emptyList()
        val commentIds = replies.map { it.id } + id

        (replies + comment)
            .mapNotNull { it.imagePublicId }
            .forEach { cloudinaryService.destroy(it) }

        commentLikeRepository.deleteByCommentIds(commentIds)
        commentRepository.deleteRepliesByParentId(id)
        commentRepository.delete(comment)
    }

    private fun assemble(comments: List<Comment>, includeReplyCount: Boolean): List<CommentDto> {
        if (comments.isEmpty()) return emptyList()

        val userId = securityService.getCurrentUserId()
        val commentIds = comments.map { it.id }
        val authors = userRepository.findAllById(comments.map { it.authorId }.distinct())
            .associateBy({ it.id }, { it.toAuthorDto() })
        val likeCounts = commentLikeRepository.countLikesByCommentIds(commentIds)
            .associate { it[0] as Long to it[1] as Long }
        val likedIds = commentLikeRepository.findLikedCommentIds(userId, commentIds).toSet()
        val replyCounts = if (includeReplyCount) {
            commentRepository.countRepliesByParentIds(commentIds)
                .associate { it[0] as Long to it[1] as Long }
        } else {
            emptyMap()
        }

        return comments.map { comment ->
            CommentDto(
                id = comment.id,
                author = authors[comment.authorId] ?: AuthorDto(id = comment.authorId, name = DELETED_USER_NAME),
                mediaType = comment.mediaType,
                mediaId = comment.mediaId,
                parentId = comment.parent?.id,
                content = comment.content,
                imageUrl = comment.imageUrl,
                likeCount = likeCounts[comment.id] ?: 0L,
                likedByMe = comment.id in likedIds,
                replyCount = replyCounts[comment.id] ?: 0L,
                edited = comment.edited,
                createdAt = comment.createdAt,
            )
        }
    }

    private fun validateMediaExists(mediaType: MediaType, mediaId: Long) {
        val exists = when (mediaType) {
            MediaType.MOVIE -> movieRepository.existsById(mediaId)
            MediaType.SERIES -> seriesRepository.existsById(mediaId)
        }
        if (!exists) {
            throw ApiException(ErrorCode.MEDIA_NOT_FOUND)
        }
    }

    companion object {
        private const val PAGE_SIZE = 20
        private const val DELETED_USER_NAME = "Deleted user"
    }
}
