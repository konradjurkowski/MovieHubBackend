package com.konradjurkowski.moviehub_server.core.model.dto.response

data class ErrorResponse(
    val message: String? = null,
    val code: String = ErrorCode.GENERIC_ERROR.name,
) : ApiResponse

enum class ErrorCode {
    // Authentication
    ACCOUNT_NOT_ACTIVATED,
    INVALID_CREDENTIALS,
    EMAIL_ALREADY_EXISTS,
    INVALID_REFRESH_TOKEN,
    USER_NOT_AUTHENTICATED,

    // Verification code
    VERIFICATION_CODE_EXPIRED,
    INVALID_ACTIVATION_ACCOUNT_CODE,
    INVALID_RESET_PASSWORD_CODE,

    // Invitation codes
    INVALID_INVITATION_CODE_FORMAT,
    INVITATION_CODE_NOT_FOUND,
    UNABLE_TO_GENERATE_INVITATION_CODE,

    // User errors
    USER_NOT_FOUND,

    // Movies error
    MOVIE_ALREADY_EXISTS,

    // Other
    TOO_MANY_ATTEMPTS,
    TOO_MANY_REQUESTS,
    GENERIC_ERROR,
}
