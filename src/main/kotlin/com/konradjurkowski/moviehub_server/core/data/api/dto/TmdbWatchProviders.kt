package com.konradjurkowski.moviehub_server.core.data.api.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class TmdbWatchProviders(val results: TmdbWatchProviderData)

@JsonIgnoreProperties(ignoreUnknown = true)
data class TmdbWatchProviderData(
    @param:JsonProperty("AT")
    val at: TmdbWatchProvider? = null,
    @param:JsonProperty("DE")
    val de: TmdbWatchProvider? = null,
    @param:JsonProperty("ES")
    val es: TmdbWatchProvider? = null,
    @param:JsonProperty("FR")
    val fr: TmdbWatchProvider? = null,
    @param:JsonProperty("GB")
    val gb: TmdbWatchProvider? = null,
    @param:JsonProperty("IT")
    val it: TmdbWatchProvider? = null,
    @param:JsonProperty("PL")
    val pl: TmdbWatchProvider? = null,
    @param:JsonProperty("US")
    val us: TmdbWatchProvider? = null,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class TmdbWatchProvider(
    val link: String,
    val rent: List<TmdbWatchProviderInfo>? = null,
    val buy: List<TmdbWatchProviderInfo>? = null,
    val flatrate: List<TmdbWatchProviderInfo>? = null,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class TmdbWatchProviderInfo(
    @param:JsonProperty("provider_id")
    val providerId: Long,
    @param:JsonProperty("provider_name")
    val providerName: String,
    @param:JsonProperty("logo_path")
    val logoPath: String? = null,
    @param:JsonProperty("display_priority")
    val displayPriority: Long,
)
