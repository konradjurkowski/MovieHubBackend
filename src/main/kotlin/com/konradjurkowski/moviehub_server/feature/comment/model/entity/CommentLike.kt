package com.konradjurkowski.moviehub_server.feature.comment.model.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant

@Entity
@Table(
    name = "comment_likes",
    uniqueConstraints = [UniqueConstraint(columnNames = ["user_id", "comment_id"])],
)
@EntityListeners(AuditingEntityListener::class)
class CommentLike(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(name = "user_id", nullable = false)
    val userId: Long,
    @Column(name = "comment_id", nullable = false)
    val commentId: Long,
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: Instant = Instant.now(),
) {
    override fun equals(other: Any?): Boolean =
        this === other || (other is CommentLike && id != 0L && id == other.id)

    override fun hashCode(): Int = id.hashCode()
}
