package com.konradjurkowski.moviehub_server.feature.movie.repository

import com.konradjurkowski.moviehub_server.feature.movie.model.entity.Movie
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface MovieRepository : JpaRepository<Movie, Long> {
    fun findByGroupId(groupId: Long, pageable: Pageable): Page<Movie>
    fun findByGroupIdAndTmdbId(groupId: Long, tmdbId: Long): Movie?
    fun existsByGroupIdAndTmdbId(groupId: Long, tmdbId: Long): Boolean

    @Query("SELECT m.tmdbId FROM Movie m WHERE m.groupId = :groupId")
    fun findMovieTmdbIdsByGroupId(@Param("groupId") groupId: Long): List<Long>
}
