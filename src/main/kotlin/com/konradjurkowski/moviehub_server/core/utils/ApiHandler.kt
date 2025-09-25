package com.konradjurkowski.moviehub_server.core.utils

import com.konradjurkowski.moviehub_server.core.model.dto.response.ApiResponse
import com.konradjurkowski.moviehub_server.core.model.dto.response.ErrorCode
import com.konradjurkowski.moviehub_server.core.model.dto.response.ErrorResponse
import com.konradjurkowski.moviehub_server.core.utils.exceptions.ApiException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import reactor.core.publisher.Mono

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

    inline fun <reified T : ApiResponse> executeReactive(
        status: HttpStatus = HttpStatus.OK,
        crossinline action: () -> Mono<T>
    ): Mono<ResponseEntity<ApiResponse>> =
        action()
            .map<ApiResponse> { it }
            .map { body -> ResponseEntity.status(status).body(body) }
            .onErrorResume { ex -> Mono.just(handleException(ex)) }

    fun handleException(exception: Throwable): ResponseEntity<ApiResponse> {
        if (exception is ApiException) {
            val errorResponse = ErrorResponse(code = exception.errorCode.name)
            return ResponseEntity.badRequest().body(errorResponse)
        }

        val errorResponse = ErrorResponse(message = exception.message, code = ErrorCode.GENERIC_ERROR.name)
        return ResponseEntity.badRequest().body(errorResponse)
    }
}
