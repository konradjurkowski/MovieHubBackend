package com.konradjurkowski.moviehub_server.core.model

import jakarta.servlet.http.HttpServletRequest

data class ClientInfo(
    val deviceInfo: String? = null,
    val ipAddress: String? = null,
)

fun HttpServletRequest.toClientInfo(): ClientInfo {
    return ClientInfo(deviceInfo = getHeader("User-Agent"), ipAddress = remoteAddr)
}
