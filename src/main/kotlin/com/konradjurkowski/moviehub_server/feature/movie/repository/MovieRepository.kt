package com.konradjurkowski.moviehub_server.feature.movie.repository

import com.konradjurkowski.moviehub_server.feature.movie.model.entity.Movie
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MovieRepository : JpaRepository<Movie, Long> {
    fun findByGroupId(groupId: Long, pageable: Pageable): Page<Movie>
}
