package com.konradjurkowski.moviehub_server.feature.movie.repository

import com.konradjurkowski.moviehub_server.feature.movie.model.entity.Movie
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface MovieRepository : JpaRepository<Movie, Long> {
    fun existsByTmdbId(tmdbId: Long): Boolean
    @Query("SELECT m.tmdbId from Movie m")
    fun findAllTmdbIdsAsList(): List<Long>
}
