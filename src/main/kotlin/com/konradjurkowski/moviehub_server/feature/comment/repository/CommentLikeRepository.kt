package com.konradjurkowski.moviehub_server.feature.comment.repository

import com.konradjurkowski.moviehub_server.feature.comment.model.entity.CommentLike
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface CommentLikeRepository : JpaRepository<CommentLike, Long> {

    fun findByUserIdAndCommentId(userId: Long, commentId: Long): CommentLike?

    fun countByCommentId(commentId: Long): Long

    @Query("SELECT cl.commentId, COUNT(cl) FROM CommentLike cl WHERE cl.commentId IN :commentIds GROUP BY cl.commentId")
    fun countLikesByCommentIds(commentIds: List<Long>): List<Array<Any>>

    @Query("SELECT cl.commentId FROM CommentLike cl WHERE cl.userId = :userId AND cl.commentId IN :commentIds")
    fun findLikedCommentIds(userId: Long, commentIds: List<Long>): List<Long>

    @Modifying
    @Query("DELETE FROM CommentLike cl WHERE cl.commentId IN :commentIds")
    fun deleteByCommentIds(commentIds: List<Long>)
}
