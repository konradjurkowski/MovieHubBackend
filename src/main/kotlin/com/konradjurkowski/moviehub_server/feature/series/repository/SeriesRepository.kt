package com.konradjurkowski.moviehub_server.feature.series.repository

import com.konradjurkowski.moviehub_server.feature.series.model.entity.Series
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface SeriesRepository : JpaRepository<Series, Long> {
    @Query("SELECT s.id from Series s")
    fun findAllIds(): List<Long>
}
