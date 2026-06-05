package com.konradjurkowski.moviehub_server.feature.comment.repository

import com.konradjurkowski.moviehub_server.feature.comment.model.entity.Comment
import com.konradjurkowski.moviehub_server.feature.comment.model.entity.MediaType
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface CommentRepository : JpaRepository<Comment, Long> {

    fun findByMediaTypeAndMediaIdAndParentIsNull(
        mediaType: MediaType,
        mediaId: Long,
        pageable: Pageable,
    ): Page<Comment>

    fun findByParentId(parentId: Long, pageable: Pageable): Page<Comment>

    fun findAllByParentId(parentId: Long): List<Comment>

    @Query("SELECT c.parent.id, COUNT(c) FROM Comment c WHERE c.parent.id IN :parentIds GROUP BY c.parent.id")
    fun countRepliesByParentIds(parentIds: List<Long>): List<Array<Any>>

    @Modifying
    @Query("DELETE FROM Comment c WHERE c.parent.id = :parentId")
    fun deleteRepliesByParentId(parentId: Long)
}
