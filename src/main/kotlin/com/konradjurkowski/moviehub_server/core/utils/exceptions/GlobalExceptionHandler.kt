package com.konradjurkowski.moviehub_server.core.utils.exceptions

import com.konradjurkowski.moviehub_server.core.model.dto.response.ErrorCode
import com.konradjurkowski.moviehub_server.core.model.dto.response.ErrorResponse
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.multipart.MaxUploadSizeExceededException

@RestControllerAdvice
class GlobalExceptionHandler {

    private val logger = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(ApiException::class)
    fun handleApiException(exception: ApiException): ResponseEntity<ErrorResponse> {
        val errorCode = exception.errorCode
        return ResponseEntity
            .status(errorCode.status)
            .body(ErrorResponse(code = errorCode.name))
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(exception: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        val message = exception.bindingResult.fieldErrors
            .joinToString(separator = ", ") { "${it.field}: ${it.defaultMessage}" }
        return ResponseEntity
            .status(ErrorCode.VALIDATION_ERROR.status)
            .body(ErrorResponse(message = message, code = ErrorCode.VALIDATION_ERROR.name))
    }

    @ExceptionHandler(MaxUploadSizeExceededException::class)
    fun handleMaxUploadSizeException(exception: MaxUploadSizeExceededException): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .status(ErrorCode.FILE_TOO_LARGE.status)
            .body(ErrorResponse(code = ErrorCode.FILE_TOO_LARGE.name))
    }

    @ExceptionHandler(Exception::class)
    fun handleUnexpectedException(exception: Exception): ResponseEntity<ErrorResponse> {
        logger.error("Unexpected error", exception)
        return ResponseEntity
            .status(ErrorCode.GENERIC_ERROR.status)
            .body(ErrorResponse(code = ErrorCode.GENERIC_ERROR.name))
    }
}
