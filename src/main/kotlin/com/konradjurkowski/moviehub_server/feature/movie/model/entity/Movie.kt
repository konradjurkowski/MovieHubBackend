package com.konradjurkowski.moviehub_server.feature.movie.model.entity

import com.konradjurkowski.moviehub_server.feature.group.model.entity.Group
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import org.hibernate.annotations.BatchSize
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity
@Table(
    name = "movies",
    indexes = [
        Index(name = "idx_movies_group_id", columnList = "group_id"),
        Index(name = "idx_movies_group_id_created_at", columnList = "group_id, created_at"),
        Index(name = "idx_movies_group_id_updated_at", columnList = "group_id, updated_at")
    ],
    uniqueConstraints = [
        UniqueConstraint(name = "uk_movies_group_tmdb", columnNames = ["group_id", "tmdb_id"])
    ]
)
@EntityListeners(AuditingEntityListener::class)
@BatchSize(size = 50)
class Movie(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    val group: Group,
    @Column(name = "group_id", insertable = false, updatable = false)
    val groupId: Long? = null,
    @Column(name = "tmdb_id", nullable = false)
    val tmdbId: Long,
    @Column(nullable = false)
    val title: String,
    @Column(nullable = false)
    val overview: String,
    @Column(nullable = false)
    val language: String,
    @Column(nullable = false)
    val adult: Boolean,
    @Column(name = "poster_url")
    val posterUrl: String? = null,
    @Column(name = "background_url")
    val backgroundUrl: String? = null,
    @Column(name = "release_date")
    val releaseDate: LocalDateTime? = null,
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),
    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    val updatedAt: LocalDateTime = LocalDateTime.now()
) {
    override fun equals(other: Any?): Boolean =
        this === other || (other is Movie && id != 0L && id == other.id)

    override fun hashCode(): Int = id.hashCode()
}
