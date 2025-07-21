package com.konradjurkowski.moviehub_server.core.utils.exceptions

import com.konradjurkowski.moviehub_server.core.model.dto.ErrorCode

class ApiException(val errorCode: ErrorCode = ErrorCode.GENERIC_ERROR) : Exception()
