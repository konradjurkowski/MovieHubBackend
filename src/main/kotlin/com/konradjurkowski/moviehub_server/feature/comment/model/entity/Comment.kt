package com.konradjurkowski.moviehub_server.feature.comment.model.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant

@Entity
@Table(name = "comments")
@EntityListeners(AuditingEntityListener::class)
class Comment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Enumerated(EnumType.STRING)
    @Column(name = "media_type", nullable = false)
    val mediaType: MediaType,
    @Column(name = "media_id", nullable = false)
    val mediaId: Long,
    @Column(name = "author_id", nullable = false)
    val authorId: Long,
    @Column(nullable = false, length = 2000)
    var content: String,
    @Column(name = "image_url")
    var imageUrl: String? = null,
    @Column(name = "image_public_id")
    var imagePublicId: String? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    val parent: Comment? = null,
    @Column(nullable = false)
    var edited: Boolean = false,
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: Instant = Instant.now(),
    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    var updatedAt: Instant = Instant.now(),
) {
    override fun equals(other: Any?): Boolean =
        this === other || (other is Comment && id != 0L && id == other.id)

    override fun hashCode(): Int = id.hashCode()
}

enum class MediaType {
    MOVIE, SERIES
}
