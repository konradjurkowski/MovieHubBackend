package com.konradjurkowski.moviehub_server.core.model.dto.tmdb

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class WatchProviderDataDto(
    @JsonProperty("AT")
    val at: WatchProviderDto? = null,
    @JsonProperty("DE")
    val de: WatchProviderDto? = null,
    @JsonProperty("ES")
    val es: WatchProviderDto? = null,
    @JsonProperty("FR")
    val fr: WatchProviderDto? = null,
    @JsonProperty("GB")
    val gb: WatchProviderDto? = null,
    @JsonProperty("IT")
    val it: WatchProviderDto? = null,
    @JsonProperty("PL")
    val pl: WatchProviderDto? = null,
    @JsonProperty("US")
    val us: WatchProviderDto? = null,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class WatchProviderDto(
    val link: String,
    val rent: List<WatchProviderInfoDto>? = null,
    val buy: List<WatchProviderInfoDto>? = null,
    val flatrate: List<WatchProviderInfoDto>? = null,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class WatchProviderInfoDto(
    @JsonProperty("provider_id")
    val providerId: Long,
    @JsonProperty("provider_name")
    val providerName: String,
    @JsonProperty("logo_path")
    val logoPath: String?,
    @JsonProperty("display_priority")
    val displayPriority: Long,
)
