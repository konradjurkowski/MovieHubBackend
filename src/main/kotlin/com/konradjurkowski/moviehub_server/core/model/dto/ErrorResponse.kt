package com.konradjurkowski.moviehub_server.core.model.dto

data class ErrorResponse(
    val message: String? = null,
    val code: String = ErrorCode.GENERIC_ERROR.name,
) : ApiResponse

enum class ErrorCode {
    // Authentication
    INVALID_CREDENTIALS,
    EMAIL_ALREADY_EXISTS,
    INVALID_REFRESH_TOKEN,
    USER_NOT_AUTHENTICATED,

    // Invitation codes
    INVALID_INVITATION_CODE_FORMAT,
    INVITATION_CODE_NOT_FOUND,
    UNABLE_TO_GENERATE_INVITATION_CODE,

    // User errors
    USER_NOT_FOUND,

    // Group errors
    GROUP_NOT_FOUND,
    USER_ALREADY_IN_GROUP,
    USER_NOT_IN_GROUP,
    CANNOT_LEAVE_LAST_ADMIN,

    // Other
    GENERIC_ERROR,
}
