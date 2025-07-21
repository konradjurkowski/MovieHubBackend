package com.konradjurkowski.moviehub_server.core.utils

import com.konradjurkowski.moviehub_server.core.model.dto.ApiResponse
import com.konradjurkowski.moviehub_server.core.model.dto.ErrorCode
import com.konradjurkowski.moviehub_server.core.model.dto.ErrorResponse
import com.konradjurkowski.moviehub_server.core.utils.exceptions.ApiException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

object ApiHandler {

    inline fun execute(
        status: HttpStatus = HttpStatus.OK,
        crossinline action: () -> ApiResponse,
    ): ResponseEntity<ApiResponse> {
        return try {
            ResponseEntity.status(status).body(action())
        } catch (exception: Exception) {
            handleException(exception)
        }
    }

    fun handleException(exception: Exception): ResponseEntity<ApiResponse> {
        if (exception is ApiException) {
            val errorResponse = ErrorResponse(code = exception.errorCode.name)
            return ResponseEntity.badRequest().body(errorResponse)
        }

        val errorResponse = ErrorResponse(message = exception.message, code = ErrorCode.GENERIC_ERROR.name)
        return ResponseEntity.badRequest().body(errorResponse)
    }
}
