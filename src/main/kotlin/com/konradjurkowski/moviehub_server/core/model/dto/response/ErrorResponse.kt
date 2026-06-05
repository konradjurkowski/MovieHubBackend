package com.konradjurkowski.moviehub_server.core.model.dto.response

import org.springframework.http.HttpStatus

data class ErrorResponse(
    val message: String? = null,
    val code: String = ErrorCode.GENERIC_ERROR.name,
)

enum class ErrorCode(val status: HttpStatus) {
    // Authentication
    ACCOUNT_NOT_ACTIVATED(HttpStatus.FORBIDDEN),
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED),
    EMAIL_ALREADY_EXISTS(HttpStatus.CONFLICT),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED),
    USER_NOT_AUTHENTICATED(HttpStatus.UNAUTHORIZED),

    // Verification code
    VERIFICATION_CODE_EXPIRED(HttpStatus.BAD_REQUEST),
    INVALID_ACTIVATION_ACCOUNT_CODE(HttpStatus.BAD_REQUEST),
    INVALID_RESET_PASSWORD_CODE(HttpStatus.BAD_REQUEST),

    // Invitation codes
    INVALID_INVITATION_CODE_FORMAT(HttpStatus.BAD_REQUEST),
    INVITATION_CODE_NOT_FOUND(HttpStatus.NOT_FOUND),
    UNABLE_TO_GENERATE_INVITATION_CODE(HttpStatus.INTERNAL_SERVER_ERROR),

    // User errors
    USER_NOT_FOUND(HttpStatus.NOT_FOUND),

    // Movies error
    MOVIE_ALREADY_EXISTS(HttpStatus.CONFLICT),

    // Series error
    SERIES_ALREADY_EXISTS(HttpStatus.CONFLICT),

    // Validation
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST),

    // Other
    TOO_MANY_ATTEMPTS(HttpStatus.TOO_MANY_REQUESTS),
    TOO_MANY_REQUESTS(HttpStatus.TOO_MANY_REQUESTS),
    GENERIC_ERROR(HttpStatus.INTERNAL_SERVER_ERROR),
}
