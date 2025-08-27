package com.konradjurkowski.moviehub_server.feature.tv_show.repository

import com.konradjurkowski.moviehub_server.feature.tv_show.model.entity.TvShow
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TvShowRepository : JpaRepository<TvShow, Long> {
    fun findByGroupId(groupId: Long, pageable: Pageable): Page<TvShow>
}
