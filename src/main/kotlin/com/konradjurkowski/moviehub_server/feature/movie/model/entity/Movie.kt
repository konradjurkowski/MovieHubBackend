package com.konradjurkowski.moviehub_server.feature.movie.model.entity

import com.konradjurkowski.moviehub_server.feature.movie.model.dto.MovieDto
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import org.hibernate.annotations.BatchSize
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant

@Entity
@Table(
    name = "movies",
    uniqueConstraints = [
        UniqueConstraint(name = "uk_movies_tmdb", columnNames = ["tmdb_id"])
    ],
)
@EntityListeners(AuditingEntityListener::class)
@BatchSize(size = 50)
class Movie(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(name = "tmdb_id", nullable = false)
    val tmdbId: Long,
    @Column(nullable = false)
    val title: String,
    @Column(nullable = false, length = 1000)
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
    val releaseDate: String? = null,
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: Instant = Instant.now(),
    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    val updatedAt: Instant = Instant.now(),
) {
    override fun equals(other: Any?): Boolean =
        this === other || (other is Movie && id != 0L && id == other.id)

    override fun hashCode(): Int = id.hashCode()
}

fun Movie.toDto(): MovieDto {
    return MovieDto(
        id = id,
        tmdbId = tmdbId,
        title = title,
        overview = overview,
        language = language,
        adult = adult,
        posterUrl = posterUrl,
        backgroundUrl = backgroundUrl,
        releaseDate = releaseDate,
    )
}
