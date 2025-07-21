package com.konradjurkowski.moviehub_server.feature.group.utils

import org.springframework.stereotype.Component
import java.security.SecureRandom

@Component
class InvitationCodeGenerator(
    private val secureRandom: SecureRandom = SecureRandom(),
) {

    fun generateCode(): String {
        val chars = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ"
        return (1..6)
            .map { chars[secureRandom.nextInt(chars.length)] }
            .joinToString("")
    }

    fun isValidFormat(code: String): Boolean {
        val validChars = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ"
        return code.length == 6 && code.all { it in validChars }
    }
}
